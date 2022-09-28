package no.oslomet.cs.algdat.Oblig2;


import java.util.ArrayList;

////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;


public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     *
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    public DobbeltLenketListe() {
        this.hode = null;
        this.hale = null;
        this.antall = 0;
        this.endringer = 0;
    }

    public DobbeltLenketListe(T[] a) {
        // Objects.requireNonNull(a);
        List<Node<T>> tempList = new ArrayList<Node<T>>();
        // this.hode = new Node<T>(a[0], null, null);
        // tempList.add(this.hode);
        // this.antall += 1;
        for (int i = 0; i < a.length; i++) {
            System.out.println(i);
            for (Node<T> node : tempList) {
                System.out.println(node.verdi);
            }
            if (a[i] != null){
                if (tempList.size() == 0){
                    this.hode = new Node<T>(a[i], null, null);
                    tempList.add(this.hode);
                    this.antall += 1;
                }
                else{
                    Node<T> node = new Node<T>(a[i], tempList.get(tempList.size()-1), null);
                    tempList.add(node);
                    this.antall += 1;
                }
            }
        }
        if (this.antall>0)
        this.hale = tempList.get(tempList.size()-1);
        
        for (int i = 0; i < this.antall-1; i++) {
            tempList.get(i).neste = tempList.get(i+1);
        }
    }

    public Liste<T> subliste(int fra, int til) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int antall() {
        return this.antall;
    }

    @Override
    public boolean tom() {
        if (this.antall == 0){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean leggInn(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean inneholder(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T hent(int indeks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indeksTil(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T fjern(int indeks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void nullstill() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        Node<T> current = this.hode;
        stringBuilder.append(" " + current.verdi);
        System.out.println(stringBuilder);
        for (int i = 0; i < this.antall()-1; i++) {
            System.out.println(i);
            current = current.neste;
            stringBuilder.append(" " +current.verdi);
            System.out.println(stringBuilder);
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public String omvendtString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ ");
        Node<T> current = this.hale;
        stringBuilder.append(current.verdi + " ");
        System.out.println(stringBuilder);
        for (int i = 0; i < this.antall()-1; i++) {
            System.out.println(i);
            current = current.forrige;
            stringBuilder.append(current.verdi + " ");
            System.out.println(stringBuilder);
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    public Iterator<T> iterator(int indeks) {
        throw new UnsupportedOperationException();
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        System.out.println(2334);
        Liste<String> liste = new DobbeltLenketListe<>();
        System.out.println(liste.antall() + " " + liste.tom());
        String[] s = {"asda", null, "fff", "tre5", "yt", null};
        DobbeltLenketListe<String> liste2 = new DobbeltLenketListe<>(s);
        System.out.println(liste2.antall() + " " + liste2.tom());
        System.out.println(liste2.omvendtString());
        System.out.println(liste2.toString());
    }

} // class DobbeltLenketListe


