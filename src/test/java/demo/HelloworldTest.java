package demo;

import static org.junit.Assert.*;

import org.junit.Test;

public class HelloworldTest {

	@Test
	public void test() {
		Helloworld helloworld = new Helloworld();
		String[] args = null;
		assertTrue(helloworld.main(args));
	}

}
