public class Aplicacion {
    public static void main (String arg[]){
        IFileManager fileManager = new FileManager("C:\\Users\\sacomanif\\Desktop\\file.txt");
        try {
            System.out.println(fileManager.nextChar());
            System.out.println(fileManager.nextChar());
            System.out.println(fileManager.nextChar());
            System.out.println(fileManager.nextChar());
            System.out.println(fileManager.nextChar());
            System.out.println(fileManager.nextChar());
            System.out.println(fileManager.nextChar());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
