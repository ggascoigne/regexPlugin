package regexPlugin.regexEditor;

// Illustrate the use of the scanner by reading in a file and displaying its
// tokens.

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

class Scan {
  // Get the filename from the command line

  public static void main(final String[] args) throws IOException {
    final Scan program = new Scan();
    if (args.length != 1) {
      System.out.println("Usage: java Scan filename");
    } else {
      program.scan(args[0]);
    }
  }

  // Scan each line in turn

  void scan(final String filename) throws IOException {
    final File file = new File(filename);
    final int len = (int) file.length();
    final char[] buffer = new char[len];
    final Reader in = new FileReader(file);
    in.read(buffer);
    in.close();

    long startTime = System.currentTimeMillis();
    final Scanner scanner = new RegexScanner();
    scanner.change(0, 0, len);
    scanner.scan(buffer, 0, len);

    for (int i = 0; i < scanner.size(); i++) {
      final Token t = scanner.getToken(i);
      System.out
          .println(
              "" + t.position + ": " + t.symbol.name + " " + t.symbol.type + "(" +
                  t.symbol.type.getName() + ")");
    }
    System.err
        .println("Elapsed time = " + (System.currentTimeMillis() - startTime));
  }

}
