import com.datastruct.Heap;
// import library buat generate angka random
import java.util.Random;

public class heapMain {
    public static void main(String[] args) {
        // bikin variabel buat jumlah data yang mau diproses, bisa diganti sesuai kebutuhan
        int jumlahData = 80000;

        // array buat nyimpen kunci (Integer) sebanyak jumlahData
        Integer[] kunci = new Integer[jumlahData];
        // array buat nyimpen data String sebanyak jumlahData
        String[] data = new String[jumlahData];

        // bikin objek Random biar bisa generate angka acak
        Random rand = new Random();

        // loop buat ngisi array kunci dan data
        for (int i = 0; i < jumlahData; i++) {
            // masukin angka random ke array kunci, interval nya sesuai ama variabel jumlah data
            kunci[i] = rand.nextInt(jumlahData);
            data[i] = "data ke " + i;
        }

        // bikin objek Heap, false berarti ini min-heap, kalo true itu max-heap. tinggal ganti aja
        Heap<Integer, String> heap = new Heap<Integer, String>(jumlahData, false);

        // masukin semua kunci dan data ke dalam heap satu per satu
        for (int i = 0; i < jumlahData; i++)
            heap.add(kunci[i], data[i]);

        // susun heap-nya biar sesuai konsep heap
        heap.buildHeap();

        // cetak data sebelum disortir
        System.out.println("data sblm sort:");
        for (int i = 0; i < jumlahData; i++) {
            // ambil data dari heap trs print
            System.out.println(heap.getData(i));
        }
        // catet waktu mulai sort
        long startSort = System.currentTimeMillis();

        // proses sorting pake heap sort
        heap.sort();

        // catet waktu selesai sort tapi belum print
        long finishSort = System.currentTimeMillis();

        // print data setelah sort
        System.out.println("data abis sort:");
        for (int i = 0; i < jumlahData; i++) {
            System.out.println(heap.getData(i));
        }

        // catet waktu setelah semua data udah diprint
        long finishPrint = System.currentTimeMillis();

        // hitung waktu sorting doang
        long waktuSorting = finishSort - startSort;
        // hitung waktu total dari sort sampe selesai print
        long waktuTotal = finishPrint - startSort;

        // tampilkan waktunya
        System.out.println("waktu heap sort doang: " + waktuSorting + " ms");
        System.out.println("waktu total (sort + print): " + waktuTotal + " ms");
    }
}