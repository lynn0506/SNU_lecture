public class LZWtest {
    String[] codeTable;
    int[] code;
    int pcode;
    String p;
    String c;
    String pc;

    public String encode(String line) {
        codeTable = new String[100];
        code = new int[100];
        pcode = 0;

        String encodedString = "";
        //abababbabaabbabbaabbaabbaa

        int codeNum = 0;

        codeTable[codeNum] = Character.toString(line.charAt(0));
        p = codeTable[codeNum];
        encodedString = encodedString.concat(Integer.toString(codeNum));
        c = Character.toString(line.charAt(1));
        pc = p.concat(c);

        if(c.compareTo(p) != 0) {
            codeTable[++codeNum] = c;
            encodedString = encodedString.concat(Integer.toString(codeNum));
            codeTable[++codeNum] = pc;
            encodedString = encodedString.concat(Integer.toString(codeNum));
        } else if(c.compareTo(p) == 0) {
            encodedString = encodedString.concat(Integer.toString(0));
        }
        p = Character.toString(pc.charAt(1));
        c = Character.toString(line.charAt(2));
        pc = p.concat(c);

        for(int j = 0; j <= codeNum; j++) {
            if(pc.compareTo(codeTable[j]) != 0) {
                codeTable[++codeNum] = pc;
            }
        }

        int i = 3;
        while(true) {
            if(i >= line.length()) {
                break;
            }

            p = c.concat(Character.toString(line.charAt(i++)));

            if( i == line.length()) {
                for (int k = 0; k <= codeNum; k++) {
                    if (p.compareTo(codeTable[k]) == 0) {
                        encodedString = encodedString.concat(Integer.toString(k));
                        pcode = k;
                    }
                }
            }

            for(int j = 0; j<= codeNum; j++) {
                if(p.compareTo(codeTable[j])== 0) {
                    if (j < pcode) {
                        p = c.concat(Character.toString(line.charAt(i++)));
                    } else {
                        encodedString = encodedString.concat(Integer.toString(j));
                        pcode = j;
                        break;
                    }
                }
            }

            if(i >= line.length()) {
                break;
            }
            c = Character.toString(line.charAt(i++));
            pc = p.concat(c);



            int tempNum = codeNum;
            boolean check = false;
            for(int j = 0; j <= tempNum; j++) {
                if(pc.compareTo(codeTable[j]) == 0) {
                    check = true;
                    break;
                }
            }
            if(check == false) {
                codeTable[++codeNum] = pc;
            }

        }

        return encodedString;
    }

    public String decode(String line) {
        String decodedString = "";

        for(int i = 0; i < line.length(); i++) {
             decodedString = decodedString.concat(codeTable[line.charAt(i) - '0']);
        }

        return decodedString;
    }



}

