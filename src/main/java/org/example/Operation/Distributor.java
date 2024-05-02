package org.example.Operation;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

public class Distributor {
    ReaderXLS reader = new ReaderXLS();
    Repository rep = new Repository();
    public void Import(File file, String which, boolean a) throws IOException, FileNotFoundException, InvalidFormatException{
        rep.setMatrix(reader.readXLSX(file,which,a));
    }

    public void Export(String directoryPath) throws IOException{
        reader.writeXLSX(rep.getMatrix(), directoryPath);
    }

    public void Calculate(){
        rep.setParameters();
    }
}
