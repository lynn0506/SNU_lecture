#include <linux/debugfs.h>
#include <linux/kernel.h>
#include <linux/module.h>
#include <linux/uaccess.h>
#include <linux/list.h>
#include <linux/slab.h>
#include <linux/init.h> 

/* EUNJOO JUNG 2014-19498 */
MODULE_LICENSE("GPL");
MODULE_AUTHOR("Eunjoo Jung");

#define MAX_LEN 512

static struct dentry *dir, *inputdir, *ptreedir;
static struct task_struct *curr;

int str_len = 0;
char *output_buff;

/*
 * str_node:
 * using list_head for tracking the directories
 */
struct str_node {
    struct list_head list;
    char val[MAX_LEN];
    int val_len;
};
/* LIST_HEAD - str_list set!*/
static LIST_HEAD(str_list);


/* writing process Id to the input */
static ssize_t write_pid_to_input(struct file *fp, 
                                  const char __user *user_buffer,
                                  size_t length,
                                  loff_t *position)
{
    /* for tracing the directories */
    pid_t input_pid;
    struct str_node *node;
    struct list_head *ptr, *ptrn;
    char *tmp_buff;
    int cnt;

    /* initailize the output & temporary buffer, and the length */
    str_len = 0;
    cnt = 0;
    output_buff = (char *)kmalloc(MAX_LEN*sizeof(char), GFP_KERNEL);
    tmp_buff = (char *)kmalloc(MAX_LEN*sizeof(char), GFP_KERNEL);
    
    memset(tmp_buff, 0, MAX_LEN);
    memset(output_buff, 0, MAX_LEN);

    /*
     * from user_buffer to coutput_buffer with length
     * copying data from user space to kernel space
     */
    if(copy_from_user(output_buff, user_buffer, length))
        return -EFAULT;

    /* find task_struct using input_pid. */
    sscanf(user_buffer, "%u", &input_pid);
    curr = pid_task(find_get_pid(input_pid), PIDTYPE_PID);
    if(!curr)
        return -EINVAL;
    
    /* tracing process tree from input_pid to init(1) process */
    while(curr->pid != 0) {
        memset(tmp_buff, 0, MAX_LEN);
        /* malloc for the node */
        node = kmalloc(sizeof(struct str_node), GFP_KERNEL);
        /* output format string: process_command (process_id) */
        node->val_len = sprintf(tmp_buff, "%s (%d)", curr->comm, curr->pid);
        strcpy(node->val, tmp_buff);
        node->val[node->val_len] = '\0';
        
        INIT_LIST_HEAD(&node->list);
        
        /*
         * add the node to the head of the list so that
         * the directories can be reported from parent to children
         */
        list_add(&node->list, &str_list);
        /* move to parent directory */
        curr = curr->parent;
    }
    
    /* tracing the directory and writing to the output */
    list_for_each(ptr, &str_list) {
        node = list_entry(ptr, struct str_node, list);
        str_len += sprintf(output_buff+str_len, "%s\n", node->val);
        if(str_len > MAX_LEN * cnt) {
            cnt++;
            output_buff = (char *)krealloc(output_buff, (MAX_LEN << cnt)*sizeof(char), GFP_KERNEL);
        }
    }
    /* removing the node from the list and freeing! */
    list_for_each_safe(ptr, ptrn, &str_list) {
        node = list_entry(ptr, struct str_node, list);
        list_del(ptr);
        kfree(node);
    }
    kfree(tmp_buff);
    return length;
}

/* reading output */
static ssize_t read_output(struct file *fp,
                           char __user *user_buffer,
                           size_t length,
                           loff_t *position )
{
    ssize_t total_len;
    /* read the output from the output buffer to user buffer*/
    total_len = simple_read_from_buffer(user_buffer, length, position, output_buff, str_len);
    return total_len;
}

/* write file operation */
static const struct file_operations write_dbfs_fops = {
    /* Mapping file operation write with write_output function */
    .write = write_pid_to_input,
};

/* read file operation */
static const struct file_operations read_dbfs_fops = {
    /* Mapping file operation read with read_output function */
    .read = read_output,
};

/* initializing dbfs */
static int __init dbfs_module_init(void)
{
    /* creating the directory */
    dir = debugfs_create_dir("ptree", NULL);
    if (!dir) {
        printk("Cannot create ptree dir\n");
        return -1;
    }
    /* input directory - write & read */
    inputdir = debugfs_create_file("input", S_IRUSR|S_IWUSR, dir, NULL, &write_dbfs_fops);
    if (!inputdir) {
        printk("Cannot create input dir file\n");
        return -1;
    }
    /* ptree directory - write & read */
    ptreedir = debugfs_create_file("ptree", S_IRUSR, dir, NULL, &read_dbfs_fops);
    if (!ptreedir) {
        printk("Cannot create ptree dir file\n");
        return -1;
    }
	printk("dbfs_ptree module initialize done\n");
    return 0;
}

/* exit the dbfs */
static void __exit dbfs_module_exit(void)
{
    /* removing */
    kfree(output_buff);
    debugfs_remove_recursive(dir);
	printk("dbfs_ptree module exit\n");
}

module_init(dbfs_module_init);
module_exit(dbfs_module_exit);
