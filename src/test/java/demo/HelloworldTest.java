package demo;

import static org.junit.Assert.*;

import org.junit.Test;

public class HelloworldTest {

	@Test
	public void test() {
		Helloworld helloworld = new Helloworld();
		assertTrue(helloworld.main());
	}

}
