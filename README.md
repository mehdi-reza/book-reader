# book-reader
It is a Java swing based simple book reader. The books "a zip file" will be read from "books" folder in application directory.

# Book File

A book is a zip file in "books" folder which should contain a file "book.info" including images representating book pages.

# Structure of book.info

First line should contain name of the book as "# book name: My Example Book. Further lines are read as index entries. Following is an example


## book name: قاموس الجدید
ا					009.jpg
ا ۔۔۔ ب				009.jpg
ا ۔۔۔ ب				010.jpg
ا ۔۔۔ ت				010.jpg
ا ۔۔۔ ت				011.jpg
ا ۔۔۔ ث				011.jpg

Please note the index name "ا ۔۔۔ ب" and name of the image "009.jpg" should be seperated by atleast one tab "\t" character.

