/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comics;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author mehdi.raza
 */
public class RegExTester {

    public static void main(String[] arg) {
        //new RegExTester().bookNameInArchive();
        //new RegExTester().validBookArchive();
        new RegExTester().tocLine();
    }

    private void bookNameInArchive() {
        Pattern p = Pattern.compile("#\\s*Book Name:\\s*(.*)", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher("# Book Name:    VPN Guide for Windows 7");
        m.find();
        System.out.println(m.group(1));
    }

    private void validBookArchive() {
        System.out.print("book2.zip".contains(".zip"));
    }

    private void tocLine() {
        Pattern p=Pattern.compile("(\\w*)\\t*\\w");
        Matcher m=p.matcher("عرض ناشر								0003.JPG");
        m.find();
        System.out.print(m.group(1));
    }
}
