import com.datastruct.*;
public class tree23Main {
    public static void main(String[] args) {
        nmTree<Integer, Character> theTree = new nmTree<>();

        theTree.insert(50, 'A');
        theTree.insert(40, 'B');
        theTree.insert(60, 'C');
        theTree.insert(30, 'D');
        theTree.insert(70, 'E');
        theTree.insert(21, 'F');
        theTree.insert(19, 'G');
        theTree.insert(65, 'H');
        theTree.insert(35, 'I');
        theTree.insert(18, 'J');
        theTree.insert(17, 'K');
        theTree.insert(75, 'L');
        theTree.insert(73, 'N');
        theTree.insert(80, 'M');
        
        theTree.displayTree();
        
        Character found = theTree.find(30);  //int di ubah menjadi karakter sehingga dapat menampilkan hurufnya
        if (found != null) //-1 diubah menjadi null untuk dapat menggunakan tipe data character
            System.out.println("30 ditemukan di tree");
        else
            System.out.println("30 tidak ditemukan di tree");

        theTree.delete(60); //kita sudah mencoba 
        System.out.println("2-3 tree after delete:");
        theTree.displayTree();
    }
}