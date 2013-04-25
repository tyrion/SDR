using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using SDR;
using System.Collections.Generic;
using System.Linq;

namespace SDRTests
{
    [TestClass]
    public class DequeTests
    {
        [TestMethod]
        public void TestFirstElement()
        {
            var deque = new Deque<int>(5);
            deque.PushRight(1);
            Assert.AreEqual(1, deque.Count);
            Assert.AreEqual(1, deque.PopRight());
            Assert.AreEqual(0, deque.Count);

            deque.PushRight(2);
            Assert.AreEqual(1, deque.Count);
            Assert.AreEqual(2, deque.PopLeft());
            Assert.AreEqual(0, deque.Count);

            deque.PushLeft(3);
            Assert.AreEqual(1, deque.Count);
            Assert.AreEqual(3, deque.PopRight());
            Assert.AreEqual(0, deque.Count);

            deque.PushLeft(4);
            Assert.AreEqual(1, deque.Count);
            Assert.AreEqual(4, deque.PopLeft());
            Assert.AreEqual(0, deque.Count);
        }
        [TestMethod]
        public void TestPopRight()
        {
            var deque = new Deque<int>(6);
            deque.PushRight(4);
            deque.PushRight(5);
            deque.PushRight(6);
            deque.PushLeft(3);
            deque.PushLeft(2);
            deque.PushLeft(1);
            Assert.AreEqual(6, deque.PopRight());
            Assert.AreEqual(5, deque.PopRight());
            Assert.AreEqual(4, deque.PopRight());
            Assert.AreEqual(3, deque.PopRight());
            Assert.AreEqual(2, deque.PopRight());
            Assert.AreEqual(1, deque.PopRight());
        }
        [TestMethod]
        public void TestPopLeft()
        {
            var deque = new Deque<int>(6);
            deque.PushRight(4);
            deque.PushRight(5);
            deque.PushRight(6);
            deque.PushLeft(3);
            deque.PushLeft(2);
            deque.PushLeft(1);
            Assert.AreEqual(1, deque.PopLeft());
            Assert.AreEqual(2, deque.PopLeft());
            Assert.AreEqual(3, deque.PopLeft());
            Assert.AreEqual(4, deque.PopLeft());
            Assert.AreEqual(5, deque.PopLeft());
            Assert.AreEqual(6, deque.PopLeft());
        }
        [TestMethod]
        public void TestPop()
        {
            var deque = new Deque<int>(6);
            deque.PushRight(4);
            deque.PushLeft(3);
            deque.PushRight(5);
            deque.PushLeft(2);
            deque.PushRight(6);
            deque.PushLeft(1);
            Assert.AreEqual(6, deque.PopRight());
            Assert.AreEqual(1, deque.PopLeft());
            Assert.AreEqual(5, deque.PopRight());
            Assert.AreEqual(2, deque.PopLeft());
            Assert.AreEqual(4, deque.PopRight());
            Assert.AreEqual(3, deque.PopLeft());
        }
        [TestMethod]
        public void TestOverflow()
        {
            var deque = new Deque<int>(3);
            deque.PushRight(4);
            deque.PushRight(5);
            deque.PushRight(6);
            deque.PushRight(0);

            Assert.AreEqual(5, deque.PopLeft());
            Assert.AreEqual(0, deque.PopRight());
        }
        [TestMethod]
        public void TestEnumerable()
        {
            var deque = new Deque<int>(3);
            deque.PushRight(4);
            deque.PushRight(5);
            deque.PushRight(6);
            CollectionAssert.AreEqual(new int[] { 4, 5, 6 }, deque.ToArray<int>());
        }
        [TestMethod]
        public void TestEnumerable2()
        {
            var deque = new Deque<int>(3);
            deque.PushLeft(3);
            deque.PushLeft(2);
            deque.PushLeft(1);
            CollectionAssert.AreEqual(new int[] { 1, 2, 3 }, deque.ToArray<int>());
        }
        [TestMethod]
        public void TestEnumerableOverflow()
        {
            var deque = new Deque<int>(3);
            deque.PushLeft(3);
            deque.PushLeft(2);
            deque.PushRight(1);
            CollectionAssert.AreEqual(new int[] { 2, 3, 1 }, deque.ToArray<int>());
        }
    }
}
