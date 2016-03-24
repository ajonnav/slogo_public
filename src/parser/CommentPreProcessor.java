package parser;

public class CommentPreProcessor implements IPreProcessor {

    @Override
    public String preProcess (String text) {
        StringBuilder sbText = new StringBuilder();
        int i = 0;
        while (i < text.length()) {
            if (text.charAt(i) == '#') {
                i++;
                while (text.charAt(i) != '\n' && i < text.length() - 1) {
                    i++;
                }
            }
            sbText.append(text.charAt(i));
            i++;
        }
        return sbText.toString().trim();
    }
}
