package es.datastructur.synthesizer;
import java.util.Iterator;


public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        first = 0;
        last = 0;
        fillCount = 0;
        rb = (T[]) new Object[capacity];
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) throws RuntimeException{
        if (fillCount >= capacity()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        last = (last + 1) % capacity();
        fillCount++;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() throws RuntimeException{
        if (fillCount == 0) {
            throw new RuntimeException("Ring Buffer underflow");
        }
        int oldf = first;
        first = (first + 1) % capacity();
        fillCount--;
        return rb[oldf];
    }

    @Override
    public boolean equals(Object o) {
        ArrayRingBuffer b = (ArrayRingBuffer) o;
        if (fillCount() != b.fillCount()) {
            return false;
        }
        Iterator it = iterator();
        Iterator it2 = b.iterator();
        while (it.hasNext()) {
            if (it.next() != it2.next())
                return false;
        }
        return true;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() throws RuntimeException {
        if (fillCount == 0)
            throw new RuntimeException("Ring buffer underflow");
        return rb[first];
    }

    @Override
    public int capacity() {
        return rb.length;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    private  class ArrayRingBufferIterator implements Iterator<T> {
        private ArrayRingBuffer<T> arr;
        public ArrayRingBufferIterator () {
            int c = fillCount;
            arr = new ArrayRingBuffer<>(c);
            for (int i = 0; i < c; i++) {
                T f = dequeue();
                arr.enqueue(f);
                enqueue(f);
            }
        }
        public boolean hasNext() {
            return arr.fillCount() != 0;
        }

        public T next() {
            return arr.dequeue();
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    // TODO: When you get to part 4, implement the needed code to support
    //       iteration and equals.
}
    // TODO: Remove all comments that say TODO when you're done.
