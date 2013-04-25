using System;
using System.Linq;
using System.Collections.Generic;
using System.Globalization;
using System.IO;

namespace SDR
{
    class Homework1
    {
        public static void Main(string[] args)
        {
            if (args.Length < 3)
            {
                Console.WriteLine("Numero errato di argomenti!");
                Console.WriteLine("--- SDR.exe F1 F2 INPUTFILE");
                return;
            }
            int f1 = int.Parse(args[0]);
            int f2 = int.Parse(args[1]);

            int gcd = GCD(f1, f2);
            f1 /= gcd;
            f2 /= gcd;

            Console.WriteLine("F1/F2 => {0}/{1}", f1, f2);

            // Costruisco la Sinc e la memorizzo dentro "ys"
            Func<int, double> Sinc = (n) =>
            {
                double x = Math.PI * n / f1;
                return x == 0 ? 1 : Math.Sin(x) / x;
            };

            int maxlen = 10 * f1 + 1;
            var ys = Enumerable.Range(-(5 * f1), maxlen).Select(Sinc);

            Console.WriteLine("Uso la Sinc:");
            foreach (var y in ys)
                Console.WriteLine(y);
            Console.WriteLine("======================");

            var input = LeggiInput(new StreamReader(args[2]));
            var espansione = Espansione(input, f1 - 1);
            var convoluzione = Convoluzione(espansione, ys, maxlen);
            var decimazione = Decimazione(convoluzione, f2);

            foreach (var o in decimazione)
                Console.WriteLine(o);
            
        }


        // Massimo Comun Divisore
        public static int GCD(int a, int b)
        {
            while (b > 0)
            {
                int tmp = a % b;
                a = b;
                b = tmp;
            }
            return a;
        }

        public static IEnumerable<double> LeggiInput(TextReader input)
        {
            string line;
            while (null != (line = input.ReadLine()))
                yield return Double.Parse(line, CultureInfo.InvariantCulture);
        }

        public static IEnumerable<double> Espansione(IEnumerable<double> xs, int f)
        {
            foreach (var sample in xs)
            {
                yield return sample;
                for (int i = 0; i < f; i++)
                    yield return 0;
            }
        }

        public static IEnumerable<double> Convoluzione(IEnumerable<double> xs, IEnumerable<double> ys, int maxlen)
        {
            /* Poiché il segnale e la sinc hanno molto probabilmente lunghezza diverse
             * sono costretto ad iterare manualmente su uno dei due usando .MoveNext(). */
            var signal = xs.GetEnumerator();

            /* Queste due code contengono i campioni correnti su cui sto effettuando
             * la convoluzione.
             * La classe Deque rappresenta una coda alla quale possono essere aggiunti
             * e rimossi elementi sia in testa che in coda. Inoltre quando la coda è
             * piena, aggiungere dei campioni da un lato causerà la rimozione di
             * altrettanti campioni dal lato opposto. */
            var x_deque = new Deque<double>(maxlen);
            var y_deque = new Deque<double>(maxlen);

            // Parte iniziale: segnale e sinc parzialmente sovrapposti.
            foreach (var y in ys)
            {
                if (signal.MoveNext())
                {
                    x_deque.PushRight(signal.Current);
                    y_deque.PushLeft(y);
                    yield return SommaProdotti(x_deque, y_deque);
                }
                else throw new Exception("Il segnale è più corto della Sinc");
            }
            // Parte centrale: completamente sovrapposti.
            while (signal.MoveNext())
            {
                x_deque.PushRight(signal.Current);
                yield return SommaProdotti(x_deque, y_deque);
            }

            // Parte finale: segnale terminato, parzialmente sovrapposti.
            for (int i = 0; i < maxlen-1; i++)
            {
                x_deque.PopLeft();
                yield return SommaProdotti(x_deque, y_deque);
            }
        }

        public static double SommaProdotti(IEnumerable<double> xs, IEnumerable<double> ys)
        {
            return xs.Zip(ys, (x, y) => x * y).Sum();
        }

        public static IEnumerable<double> Decimazione(IEnumerable<double> zs, int f)
        {
            int i = 0;
            foreach (var z in zs)
            {
                if (i++ % f == 0)
                    yield return z;
            }
        }
    } 
}