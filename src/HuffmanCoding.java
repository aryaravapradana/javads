import com.datastruct.*;

public class HuffmanCoding {
    public static void main(String[] args) {
        // ini array huruf yg mau dienkode
        char[] charArray = { 'E', 'T', 'N', 'I', 'S' };
        // ini jumlah kemunculan huruf2 itu
        int[] charfreq = { 29, 10, 9, 5, 4 };

        // bikin pq (priority queue) pake heap min
        Heap<Integer, HuffmanNode> pq = new Heap<>(charArray.length, true);

        // masukin semua huruf + freq-nya ke pq
        for (int i = 0; i < charArray.length; i++) {
            // bikin node buat tiap huruf
            HuffmanNode node = new HuffmanNode(charfreq[i], charArray[i], null, null);
            // terus masukin ke pq, key-nya frekuensinya
            pq.insert(charfreq[i], node);
        }

        // root ini ntar nyimpen hasil akhir (pohon huffman)
        HuffmanNode root = null;
        // x & y itu node dgn freq terkecil, buat digabungin
        HuffmanNode x, y;
        int sum;

        // selama isi pq masih lebih dari 1, kita proses
        while (pq.size() > 1) {
            // ambil node freq terkecil pertama
            sum = pq.getKey(pq.first());
            x = pq.getData(pq.first());
            pq.removeFirst(); // buang dari pq

            // ambil node freq terkecil kedua
            sum += pq.getKey(pq.first());
            y = pq.getData(pq.first());
            pq.removeFirst(); // buang jg

            // gabungin x & y jadi node baru
            root = new HuffmanNode(sum, '-', x, y);
            // masukin lagi ke pq
            pq.insert(sum, root);
        }

        // udh kelar, skrg root itu node utama (akar pohon)
        // kita ambil kode2 huffmannya dari situ
        MyArrayList<String> huffman = root.getHuffmanCodes(root, charArray.length);

        // tampilkan hasilnya dlu, versi array gitu
        System.out.print("[");
        for (int i = 0; i < huffman.size(); i++) {
            // ambil huruf + codenya, pisah pake split
            String[] parts = huffman.get(i).split(" ");
            // tampilkan satu2
            System.out.print(" " + parts[0] + " " + parts[1]);
        }
        System.out.print(" ]");

        // skrg tampilkan versi tabelnya, biar rapi
        System.out.println();
        System.out.println("---------------------");
        System.out.println(" Huruf | Huffman code ");
        System.out.println("---------------------");

        for (int i = 0; i < huffman.size(); i++) {
            // ambil huruf + code dari string
            String[] parts = huffman.get(i).trim().split(" ");
            // print satu baris tabel
            System.out.println(parts[0] + "      |   " + parts[1]);
        }

        // gacor bgt gasi kata gw mah minimal 100 nilainya
        System.out.println("---------------------");
    }
}
