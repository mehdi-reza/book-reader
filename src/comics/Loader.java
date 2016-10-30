/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 *
 * @author mehdi.raza
 */
public class Loader {

    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(Loader.class.getName());
    private static Loader instance=new Loader();
        
    public static Loader getInstance() {
        return instance;
    }

    public List<Book> loadBooks() {
        
        File booksFolder=new File(BookReader.BOOKS_FOLDER);
        int bookId=0;
        List<Loader.Book> books=new ArrayList<Loader.Book>();
        
        for (File bookArchive : booksFolder.listFiles()) {
            if(bookArchive.getName().contains(".zip")) {
                LOG.log(Level.INFO, "Found Book {0}", bookArchive.getName());
                Loader.Book book = new Loader.Book(++bookId);
                book.setArchiveName(bookArchive.getName());
                books.add(book);
                
                // reads book name from book.info zip entry
                new BookReaderThread(book).start();
            }
        }
        return books;
    }
    
    private class BookReaderThread extends Thread {

        private Book book;
        
        public BookReaderThread(Book book) {
            this.book=book;            
        }
        
        @Override
        public void run() {
            ZipFile zipFile=null;
            BufferedReader reader=null;
            String bookName=null;
            Pattern p = Pattern.compile("#\\s*Book Name:\\s*(.*)", Pattern.CASE_INSENSITIVE);
            try {
                zipFile=new ZipFile(new File(BookReader.BOOKS_FOLDER, book.getArchiveName()));
                ZipEntry bookInfo=zipFile.getEntry("book.info");
                
                if(bookInfo!=null) {
                    reader=new BufferedReader(new InputStreamReader(zipFile.getInputStream(bookInfo)));
                    String firstLine=reader.readLine();                    
                    Matcher m = p.matcher(firstLine);
                    m.find();
                    try {
                        bookName=m.group(1);
                        LOG.log(Level.INFO, "Read book name from archive ..{0} {1}", new String[]{book.getArchiveName(), bookName});
                        book.setBookName(bookName);
                    }catch(IllegalStateException ise) {
                        LOG.log(Level.WARNING, String.format("Error reading file name in archive %1$s", book.getArchiveName()), ise);
                    }
                } else {
                    LOG.log(Level.WARNING, "No book.info file found in archive {0}", book.getArchiveName());
                }
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if(reader!=null) reader.close();
                    if(zipFile!=null) zipFile.close();
                } catch (IOException ex) {
                    Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }

    public static class Book {
        
        private int id;
        private String bookName;
        private String archiveName;

        public Book() {
        }
        
        public Book(int id) {
            this.id=id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBookName() {
            return bookName;
        }

        public void setBookName(String bookName) {
            this.bookName = bookName;
        }

        public String getArchiveName() {
            return archiveName;
        }

        public void setArchiveName(String archiveName) {
            this.archiveName = archiveName;
        }

        @Override
        public boolean equals(Object obj) {
            boolean flag=false;
            if(obj instanceof Loader.Book) {
                Loader.Book b=(Loader.Book)obj;
                if(b.getId()==getId()) flag=true;
            }
            return flag;
        }
    }
}
