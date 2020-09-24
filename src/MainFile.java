import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/ru/snx/webapp");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Home work --->");
        String rootPath = "C:\\javalearn\\basejava\\src";
        showDir(new File(rootPath), "");

    }

    private static void showDir(File file, String prefix) {
        File[] listFiles = file.listFiles();
        assert listFiles != null;
        for (File f : listFiles) {
            if (f.isDirectory()) {
                System.out.println(prefix + f.getName());
                showDir(f, prefix + "  ");
            } else {
                System.out.println(prefix + "*" + f.getName());
            }
        }
    }

}