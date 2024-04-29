package org.example.Operation;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

public class Distributor {
    ReaderXLS reader = new ReaderXLS();

    public void Import(File file, String which, boolean a) throws IOException, FileNotFoundException, InvalidFormatException{
        Repository rep = new Repository();
        rep.setMatrix(reader.readXLSX(file,which,a));
    }

    public void Export(String directoryPath) throws IOException{
        reader.writeXLSX(Repository.getMatrix(), directoryPath);
    }

    public void Calculate(){
        Repository.setParameters();
    }
}
