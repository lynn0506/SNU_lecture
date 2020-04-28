public class myLZW {
    String[] key;
    String[] dec;
    int len;
    int d_len;

    public myLZW() {
        key = new String[20];
        dec = new String[20];
        len = 0;
        d_len = 0;
    }
    public String encoding(String s) {
        String ret = "";
        boolean eq = false;
        for(int i=0; i<s.length(); i++) {
            for(int j=0; j<len; j++) {
                if((s.substring(i,i+1)).equals(key[j])) {
                    eq = true;
                    break;
                }
            }
            if(!eq) {
                key[len] = s.substring(i,i+1);
                dec[d_len] = s.substring(i,i+1);
                len++;
                d_len++;
            }
        }
        int pCode = 0;
        String p = key[0];
        String c = key[1];
        ret = "";

        while(true) {
            for(int j=0; j<len; j++) {
                if(s.startsWith(key[j])) {
                    p = key[j];
                    pCode = j;
                    if(s.substring(key[j].length()).length() >= 1)
                        c = s.substring(key[j].length(), key[j].length()+1);
                }
            }
            ret = ret.concat(Integer.toString(pCode));
            if(s.substring(key[pCode].length()).length() >= 1) {
                key[len] = p + c;
                len++;
            }

            s = s.substring(key[pCode].length());
            if(s.length()==0)
                break;
        }
        return ret;
    }
    public String decoding(int[] D) {
        String ret = "";
        int pCode = 0;
        String p = "";
        String lastP = "";
        for(int i=0; i<D.length; i++) {
            if(D[i] < d_len) {
                ret = ret.concat(dec[D[i]]);
                p = dec[D[i]];
                if(lastP.length() >= 1) {
                    dec[d_len] = lastP + p.substring(0, 1);
                    d_len++;
                }
            }
            else {
                p = lastP + lastP.substring(0, 1);
                ret = ret.concat(p);
                dec[d_len] = lastP + lastP.substring(0, 1);
                d_len++;
            }
            lastP = p;
        }
        return ret;
    }
}
