package uk.ac.ebi.uniprot.variation.parser;

import uk.ac.ebi.uniprot.variation.VCFMeta;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class VCFFileReader {
    private static final String HASH_2 = "##";
    private static final String HASH = "#";
    private final String filename;

    public VCFFileReader(String filename) {
        this.filename = filename;
    }

    public Stream<String> readData() {
        try {
            return Files.lines(Paths.get(filename)).filter(val -> val.startsWith(HASH));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public VCFMeta readMetaData() {
        List<String> data = new ArrayList<>();
        try (BufferedReader reader =
                new BufferedReader(new InputStreamReader(new FileInputStream(filename)))) {
            String line = null;
            boolean hasColumn = false;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(HASH_2)) {
                    data.add(line);
                } else if (line.startsWith(HASH)) {
                    hasColumn = true;
                }
                if (hasColumn && line.startsWith(HASH)) {
                    break;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        throw new UnsupportedOperationException("not implemented yet");

    }

}
