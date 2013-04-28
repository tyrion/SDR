using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System.Linq;
using SDR;

namespace SDRTests
{
    [TestClass]
    public class Homework1Tests
    {
        [TestMethod]
        public void TestDecimazione()
        {
            var seq = Enumerable.Range(0, 50).Select(x => (double)x);

            CollectionAssert.AreEqual(new double[] { 0.0, 25.0 },
                Homework1.Decimazione(seq, 25).ToArray());

            CollectionAssert.AreEqual(new double[] { 0.0, 10.0, 20.0, 30.0, 40.0},
                Homework1.Decimazione(seq, 10).ToArray());

        }
    }
}
