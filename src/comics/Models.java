/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comics;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.imageio.ImageIO;

/**
 *
 * @author mehdi.raza
 */
public class Models {

    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(Models.class.getName());
    private static final String TITLE_IMAGE_NAME = "title.jpg";

    public static class BookModel {

        private List<Loader.Book> books;
        //private Loader.Book openedBook;

        public BookModel(List<Loader.Book> books) {
            if (books == null) {
                throw new IllegalArgumentException();
            }
            this.books = books;
        }

        public IndexModel open(int bookId) throws InvalidBookException {
            LOG.log(Level.INFO, "Opening book ...{0}", bookId);
            IndexModel indexModel = null;

            for (Loader.Book book : books) {
                if (book.getId() == bookId) {
                    if(!book.isValid()) throw new InvalidBookException("Invalid book.. "+book.getArchiveName());
                    indexModel = new IndexModel(book);
                    break;
                }
            }
            return indexModel;
        }

        public Image nextBookImage(int[] after) throws IOException {
            if (after == null) {
                throw new IllegalArgumentException("after argument must not be null, to supply with the book id");
            }

            LOG.log(Level.FINEST, String.valueOf("after[] = " + after[0]));

            Image img = null;
            ZipFile zipFile = null;

            try {

                Loader.Book nextBook;
                if (after[0] == 0) // when just opened and there is no current book 
                {
                    nextBook = books.get(books.indexOf(new Loader.Book(1)));
                } else {
                    try {
                        nextBook = books.get(books.indexOf(new Loader.Book(after[0] + 1)));
                    } catch (IndexOutOfBoundsException e) {
                        nextBook = books.get(books.indexOf(new Loader.Book(1)));
                    }
                    after[0] = nextBook.getId();
                }

                LOG.info(String.format("Reading from archive.. %1$s %2$s", BookReader.BOOKS_FOLDER, nextBook.getArchiveName()));
                zipFile = new ZipFile(new File(BookReader.BOOKS_FOLDER, nextBook.getArchiveName()));
                ZipEntry entry = zipFile.getEntry(TITLE_IMAGE_NAME);
                if (entry != null) {
                    LOG.log(Level.INFO, "Zip entry ..{0}", entry.getName());
                    img = ImageIO.read(zipFile.getInputStream(entry));
                } else {
                    LOG.log(Level.INFO, "NO Title Image found in archive {0}", nextBook.getArchiveName());
                    img = ImageIO.read(this.getClass().getResourceAsStream("/comics/resources/no_title_image.png"));
                }
                after[0] = nextBook.getId(); // return book id                
            } catch (IOException ex) {
                LOG.log(Level.INFO, null, ex);
            } finally {
                if (zipFile != null) {
                    zipFile.close();
                }
            }
            return img;
        }

        public Image prevBookImage(int[] before) throws IOException {
            if (before == null) {
                throw new IllegalArgumentException("before argument must not be null, to supply with the book id");
            }

            LOG.log(Level.FINEST, String.valueOf("before[] = " + before[0]));

            BufferedImage img = null;
            ZipFile zipFile = null;

            try {

                Loader.Book prevBook;

                if (before[0] == 1) // if first book 
                {
                    prevBook = books.get(books.indexOf(new Loader.Book(books.size())));
                } else {
                    try {
                        prevBook = books.get(books.indexOf(new Loader.Book(before[0] - 1)));
                    } catch (IndexOutOfBoundsException e) {
                        prevBook = books.get(books.indexOf(new Loader.Book(books.size() - 1)));
                    }
                    before[0] = prevBook.getId();
                }

                LOG.info(String.format("Reading from archive.. %1$s %2$s", BookReader.BOOKS_FOLDER, prevBook.getArchiveName()));
                zipFile = new ZipFile(new File(BookReader.BOOKS_FOLDER, prevBook.getArchiveName()));
                ZipEntry entry = zipFile.getEntry(TITLE_IMAGE_NAME);
                if (entry != null) {
                    LOG.log(Level.INFO, "Zip entry ..{0}", entry.getName());
                    img = ImageIO.read(zipFile.getInputStream(entry));
                } else {
                    LOG.log(Level.INFO, "NO Title Image found in archive {0}", prevBook.getArchiveName());
                    img = ImageIO.read(this.getClass().getResourceAsStream("/comics/resources/no_title_image.png"));
                }
                before[0] = prevBook.getId(); // return book id    

            } catch (IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
            } finally {
                if (zipFile != null) {
                    zipFile.close();
                }
            }
            return img;
        }

        public String getTitle(int current) {
            return books.get(books.indexOf(new Loader.Book(current))).getBookName();
        }
    }

    public static class IndexModel extends javax.swing.AbstractListModel<String> {

        private Loader.Book book;
        private List<String> index = new ArrayList<String>();
        private Map<Integer,ZipEntry> indexImageMap=new HashMap<Integer,ZipEntry>();
        ZipFile zipFile = null;

        public IndexModel(Loader.Book book) throws InvalidBookException {
            if(!book.isValid()) throw new InvalidBookException("Invalid book .. "+book.getArchiveName());
            this.book = book;
        }

        public Loader.Book getBook() {
            return book;
        }

        public int getSize() {
            return index.size();
        }

        public String getElementAt(int index) {
            return this.index.get(index);
        }
        
        public Image getImage(Integer index) throws IOException {
            LOG.log(Level.INFO, "Selection changed {0}", index);
            return ImageIO.read(zipFile.getInputStream(indexImageMap.get(index)));
        }

        public void load() {
            LOG.log(Level.INFO, "Loading index for book .. {0} {1}", new String[]{book.getArchiveName(), book.getBookName()});

            BufferedReader reader = null;
            Pattern p=Pattern.compile("(\\t+)");
            Matcher m=p.matcher("");
            try {
                if(zipFile!=null) zipFile.close();
                indexImageMap.clear();
                zipFile = new ZipFile(new File(BookReader.BOOKS_FOLDER, book.getArchiveName()));
                ZipEntry toc = zipFile.getEntry(BookReader.TOC_ENTRY_NAME);
                reader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(toc)));
                reader.readLine(); // ignore book name
                String line="";
                while(line!=null) {
                    line=reader.readLine();
                    if(line!=null) {
                        m.reset(line);
                        if(m.find()) {
                            LOG.log(Level.FINEST, "Found groups in index entry ..{0} {1}", new Object[]{line, m.replaceAll("\\$")});                        
                            String[] indexImage=m.replaceAll("\\$").split("\\$");
                            index.add(indexImage[0]);
                            indexImageMap.put(index.size()-1, zipFile.getEntry(indexImage[1]));
                        }
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Models.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {                    
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Models.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
