
public class Facade{
    public static void main(String[] args) {
        String nameFile = "test.zip";

        ExtractFile extracter = new ExtractFile();

        extracter.extract(nameFile);

        nameFile = "test.tar";
        extracter.extract(nameFile);

        nameFile += ".gz";
        extracter.extract(nameFile);
        
    }
}


class ZipUnpacker {
    public static void extract(String nameFile){
        System.out.println("File zip " + nameFile + " extracted.");
    }
}

class TarUnpacker {
    public static void unpack(String nameFile){
        System.out.println("File tar " + nameFile + " unpacked.");
    }
}

class TarGzExtracter {
    public static void extract(String nameFile) {
        TarUnpacker.unpack(nameFile);
        System.out.println("File gzip " + nameFile + " extracted.");
    }
}


class ExtractFile {
    
    public void extract(String nameFile) {
        if(nameFile.endsWith(".tar.gz"))        
            TarGzExtracter.extract(nameFile);
        else if (nameFile.endsWith(".tar"))
            TarUnpacker.unpack(nameFile);
        else if(nameFile.endsWith(".zip"))
            ZipUnpacker.extract(nameFile);
        else 
            System.out.println("File not valid!");
    }
}
