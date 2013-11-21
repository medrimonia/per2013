package collections;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

public class IteratorsTest {


	@Test
	public void testFillList() {
			List<Integer> li = Arrays.asList(0, 1, 2, 3, 4, 5);
			assertTrue(li.equals(Iterators.fillList(new ArrayList<Integer>(), li.iterator())));
	}
	
	@Test
	public void testHasElements() {
		Integer[] elements = new Integer[]{0, 1, 2, 3, 4, 5};
		assertTrue(Iterators.hasElements(Arrays.asList(elements).iterator(), elements));
	}
		
	@Test
	public void testIteratorWithPredicate() {
		Iterator<Integer> it = Iterators.iteratorWithPredicate(Arrays.asList(
				new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }).iterator(),
				new Predicate<Integer>() {
					public boolean predicate(Integer o) {
						return o % 2 == 0;
					}
				});
		assertTrue(Iterators.hasElements(it, 0, 2, 4, 6, 8));
	}

	@Test
	public void testSubArrayIterator() {
		Iterator<Integer> it = Iterators.subArrayIterator(new Integer[] { 0, 1,
				2, 3, 4, 5, 6, 7, 8, 9 }, 3, 7);
		assertTrue(Iterators.hasElements(it, 3, 4, 5, 6));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testSubArrayIterator2() {
		Iterators.subArrayIterator(new Integer[] { 0, 1, 2, 3, 4 }, 3, 7);
	}

	@Test
	public void testAppend() {
		Iterator<Integer> it = Iterators.append(Arrays.asList(
				new Integer[] { 0, 1, 2, 3, 4 }).iterator(), Arrays.asList(
				new Integer[] { 5, 6, 7, 8, 9 }).iterator());
		assertTrue(Iterators.hasElements(it, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
	}

	@Test
	public void testAppendElement() {
		Iterator<Integer> it = Iterators.appendElement(0, Arrays.asList(
				new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 }).iterator());
		assertTrue(Iterators.hasElements(it, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
		it = Iterators.appendElement(Arrays.asList(
				new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 }).iterator(), 9);
		assertTrue(Iterators.hasElements(it, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testSingleton() {
		Iterator<Integer> it = Iterators.singleton(new Integer(5));
		assertTrue(Iterators.hasElements(it, 5));
		it.next();
	}
	
	@Test
	public void testSize() {
		assertTrue(Iterators.size(Arrays.asList(0, 1, 2, 3, 4, 5).iterator()) == 6);
	}
	
	
}
