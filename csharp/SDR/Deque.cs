using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;

namespace SDR
{
    public class Deque<E> : IEnumerable<E>
    {
        readonly int size;
        int head = 0;
        int tail = 0;
        E[] values;

        public int Count { get; private set; }
        public int Size { get { return size; } }

        public Deque(int size)
        {
            this.size = size;
            this.head = size - 1; 
            this.values = new E[size];
        }

        public E PopLeft()
        {
            Count--;
            head = (head + 1) % size;
            return values[head];
        }

        public E PopRight()
        {
            Count--;
            tail = Dec(tail);
            return values[tail];
        }

        public void PushRight(E item)
        {
            Push(item, ref tail, ref head, () => tail = (tail + 1) % size);
        }

        public void PushLeft(E item)
        {
            Push(item, ref head, ref tail, () => head = Dec(head));
        }

        private void Push(E item, ref int i, ref int j, Action updateIndex)
        {
            values[i] = item;
            if (Count == size)
                j = i;
            else
                Count++;
            updateIndex();
        }

        private int Dec(int i)
        {
            i--;
            return i - (int)Math.Floor((double)i / size) * size;
        }

        public IEnumerator<E> GetEnumerator()
        {
            Func<int, int> Inc = (x) => (x + 1) % Size;
            for (int i = Inc(head), j = 0; j < Count; i = Inc(i), j++)
                yield return values[i];
        }

        IEnumerator IEnumerable.GetEnumerator()
        {
            return this.GetEnumerator();
        }

        public override string ToString()
        {
            var builder = new StringBuilder();
            builder.Append("[ ");
            foreach (var i in this)
                builder.AppendFormat("{0} ", i);
            builder.Append("]");
            return builder.ToString();
        }
    } 
}