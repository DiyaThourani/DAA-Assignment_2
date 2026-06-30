
public class BoyerMoore {
    static int[] badCharTable(String pat) {
        int[] lastIndex = new int[256];
        for (int i = 0; i < 256; i++) {
            lastIndex[i] = -1;
        }
        for (int i = 0; i < pat.length(); i++) {
            lastIndex[pat.charAt(i)] = i;
        }
        return lastIndex;
    }

    static int[] goodSuffixTable(String pat) {
        int m = pat.length();
        int[] shift = new int[m + 1];
        for (int i = 0; i <= m; i++) {
            String suffix = pat.substring(i);
            int shiftAmount = -1;
            for (int s = 1; s <= m; s++) {
                boolean works = true;
                for (int k = 0; k < suffix.length(); k++) {
                    int patIndex = i + k - s;
                    if (patIndex >= 0) {
                        if (pat.charAt(patIndex) != suffix.charAt(k)) {
                            works = false;
                            break;
                        }
                    }
                }
                if (works) {
                    int beforeIndex = i - s;
                    if (beforeIndex > 0 && i > 0) {
                        if (pat.charAt(beforeIndex - 1) == pat.charAt(i - 1)) {
                            works = false;
                        }
                    }
                }

                if (works) {
                    shiftAmount = s;
                    break;
                }
            }

            if (shiftAmount == -1) {
                shiftAmount = m;
            }
            shift[i] = shiftAmount;
        }
        return shift;
    }

    static void boyerMooreSearch(String text, String pat) {
        int n = text.length();
        int m = pat.length();

        if (m == 0 || m > n) {
            System.out.println("pattern doesn't fit in text, can't search");
            return;
        }

        int[] badChar = badCharTable(pat);
        int[] goodSuffix = goodSuffixTable(pat);

        System.out.println("Text    : " + text);
        System.out.println("Pattern : " + pat);
        System.out.println("------------------------------------------------------------");

        int s = 0;
        boolean foundAny = false;

        while (s <= n - m) {
            int j = m - 1;
            while (j >= 0 && pat.charAt(j) == text.charAt(s + j)) {
                j--;
            }

            if (j < 0) {
                System.out.println("Found pattern at index " + s);
                foundAny = true;
                s = s + goodSuffix[0];
            } else {
                char badChar_text = text.charAt(s + j);
                int bcShift = j - badChar[badChar_text];
                int gsShift = goodSuffix[j + 1];
                int finalShift = bcShift;
                if (gsShift > finalShift) {
                    finalShift = gsShift;
                }
                if (finalShift < 1) {
                    finalShift = 1;
                }

                System.out.println("Mismatch at text[" + (s + j) + "], pattern[" + j +
                        "] -> bad char shift=" + bcShift + ", good suffix shift=" + gsShift +
                        ", moving pattern by " + finalShift);

                s = s + finalShift;
            }
        }

        System.out.println("------------------------------------------------------------");
        if (!foundAny) {
            System.out.println("pattern was not found in the text");
        }
    }

    public static void main(String[] args) {

        String text = "Insertion sort typically has a smaller constant factor than merge sort";
        String pattern = "sort";

        boyerMooreSearch(text, pattern);
    }
}