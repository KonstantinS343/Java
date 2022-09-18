class EnglishAlphabet {

    public static StringBuilder createEnglishAlphabet() {
        StringBuilder str = new StringBuilder();
        for (int i = 65; i <= 90 ; i++) {
            if (i == 90) {
                str.append((char) i);
                break;
            }
            str.append((char) i + " ");
        }
        return str;
    }
}