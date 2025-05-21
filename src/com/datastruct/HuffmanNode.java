package com.datastruct;

public class HuffmanNode extends BTNode<Integer, Character> {
    // list buat nyimpen hasil kode huffman (huruf + kode biner-nya)
    private MyArrayList<String> codeList;

    // constructor-nya, nerima freq, huruf, dan 2 anak (kiri & kanan)
    public HuffmanNode(int freq, char letter, HuffmanNode node1, HuffmanNode node2) {
        // masukin freq sbg key, huruf sbg data
        super(freq, letter);
        // set anak kiri
        setLlink(node1);
        // set anak kanan
        setRlink(node2);
    }

    // method buat ngambil semua kode huffman dari pohon
    public MyArrayList<String> getHuffmanCodes(HuffmanNode root, int n) {
        String s = ""; // ini bakal nyimpen kode binernya (0/1)
        codeList = new MyArrayList<String>(n); // siapin list buat hasil
        printCode(root, s); // mulai traversing pohon dari root
        return codeList; // balikin list yg udh jadi
    }

    // method bantu buat ngebangun kode biner dari pohon
    public void printCode(HuffmanNode node, String s) {
        // cek kalo node ini leaf (ga punya anak kiri & kanan)
        if (node.getLlink() == null && node.getRlink() == null) {
            // simpen huruf + kodenya ke list, misal: "E 01"
            codeList.add(node.getData() + " " + s);
            return;
        }

        // kalo masih ada anak, lanjut ke kiri dulu, tambah "1" ke string kode
        printCode((HuffmanNode) node.getLlink(), s + "1");
        // abis itu lanjut ke kanan, tambah "0"
        printCode((HuffmanNode) node.getRlink(), s + "0");
    }
}
