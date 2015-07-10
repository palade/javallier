package com.n1analytics.paillier;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.math.BigInteger;
import java.util.HashSet;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

// NOTE: decrypt() method is tested in other test classes
public class PaillierPrivateKeyTest {
//    final static Logger logger = LoggerFactory.getLogger(PaillierKeyGenerationTest.class);

    @Category(SlowTests.class)
    @Test
    public void testCreateKeypairs() throws Exception {
//        logger.debug("Running phe test: Generate keypairs with various length.");

        int[] keyLength = {8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096};

        for(int i = 0; i < keyLength.length; i++){
            PaillierPrivateKey privateKey = PaillierPrivateKey.create(keyLength[i]);

            // Check if the private key exist
            assertNotNull(privateKey);
            // Check if the public key associated with this private key exist
            assertNotNull(privateKey.getPublicKey());
            // Check if lambda exist
            assertNotNull(privateKey.getTotient());
            // Check if mu exist
            assertNotNull(privateKey.getTotientInverse());

            PaillierPublicKey publicKey = privateKey.getPublicKey();
            // Check if the public key exist
            assertNotNull(publicKey);
            // Check if n exist
            assertNotNull(publicKey.getModulus());
            // Check if n^2 exist
            assertNotNull(publicKey.getModulusSquared());
            // Check if g exist
            assertNotNull(publicKey.getGenerator());
        }
    }

    @Test
    public void testIllegalKeyLength() throws Exception {
//        logger.debug("Running test: Generate keypair with unsupported keylength.");

        PaillierPrivateKey privateKey = null;

        int keysizeSmallerThanEight = 4;
        try {
            privateKey = PaillierPrivateKey.create(keysizeSmallerThanEight);
            fail("Successfuly create a private key which key size is smaller than eight.");
        } catch (IllegalArgumentException e) {
        }

        int keysizeNotMultipleOfEight = 1023;
        try {
            privateKey = PaillierPrivateKey.create(keysizeNotMultipleOfEight);
            fail("Successfuly create a private key which key size is not a multiple of eight.");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testConstructor() throws Exception {
        PaillierPrivateKey privateKey = null;

        BigInteger modulus = new BigInteger("17").multiply(new BigInteger("19"));
        PaillierPublicKey publicKey = new PaillierPublicKey(modulus);

        // Check if exception is thrown when the public key is null
        try {
            privateKey = new PaillierPrivateKey(null, new BigInteger("144"));
            fail("Succefully created a private key with a null public key");
        } catch (IllegalArgumentException e) {
        }
        assertNull(privateKey);

        // Check if exception is thrown when the totient is null
        try {
            privateKey = new PaillierPrivateKey(publicKey, null);
            fail("Succefully created a private key with a null totient");
        } catch (IllegalArgumentException e) {
        }
        assertNull(privateKey);

        // Check if exception is thrown when the totient is negative
        try {
            privateKey = new PaillierPrivateKey(publicKey, BigInteger.ONE.negate());
            fail("Succefully created a private key with a negative totient");
        } catch (IllegalArgumentException e) {
        }
        assertNull(privateKey);

        // Check if exception is thrown when the totient is equal to modulus
        try {
            privateKey = new PaillierPrivateKey(publicKey, modulus);
            fail("Succefully created a private key with a totient value equals to the public key's modulus");
        } catch (IllegalArgumentException e) {
        }
        assertNull(privateKey);

        BigInteger totient = new BigInteger("144");
        privateKey = new PaillierPrivateKey(publicKey, totient);
        assertNotNull(privateKey);
        // Check public key
        assertNotNull(privateKey.getPublicKey());
        assertEquals(publicKey, privateKey.getPublicKey());
        // Check totient
        assertNotNull(privateKey.getTotient());
        assertEquals(totient, privateKey.getTotient());
        // Check totient inverse
        assertNotNull(privateKey.getTotientInverse());
        assertEquals(totient.modInverse(publicKey.getModulus()), privateKey.getTotientInverse());
    }

    @Test
    public void testEquals() throws Exception {
        BigInteger modulus = new BigInteger("17").multiply(new BigInteger("19"));
        BigInteger totient = new BigInteger("144");
        PaillierPublicKey publicKey = new PaillierPublicKey(modulus);
        PaillierPrivateKey privateKey = new PaillierPrivateKey(publicKey, totient);

        assertTrue(privateKey.equals(privateKey));
        assertFalse(privateKey.equals(publicKey));

        PaillierPrivateKey otherPrivateKey = null;

        // Check when the other private key hasn't been initialised (ie, is null)
        assertFalse(privateKey.equals(otherPrivateKey));

        BigInteger otherModulus = new BigInteger("13").multiply(new BigInteger("17"));
        BigInteger otherTotient = new BigInteger("192");
        new PaillierPrivateKey(new PaillierPublicKey(otherModulus), otherTotient);

        // Check after the other private key has been initialised (ie, is not null)
        assertFalse(privateKey.equals(otherPrivateKey));

        assertFalse(privateKey.equals(null));
    }

    // Key uniqueness tests for key of size 512 bits, 1024 bits and 2104 bits.
    // The selected key sizes correspond to the key size likely used in a real life scenario.
    // The number of repeats correspond to the number of unique keys need to be generated.

    @Category(SlowTests.class)
    @Test
    public void testKeyUniqueness512() throws Exception {
        int repeats = 100;
        HashSet<PaillierPrivateKey> keypairs = new HashSet<PaillierPrivateKey>();
        while(keypairs.size() < repeats) {
            PaillierPrivateKey privateKey = PaillierPrivateKey.create(512);
            if(keypairs.contains(privateKey))
                fail("Non unique keypair.");
            keypairs.add(privateKey);
        }
    }

    @Category(SlowTests.class)
    @Test
    public void testKeyUniqueness1024() throws Exception {
        int repeats = 100;
        HashSet<PaillierPrivateKey> keypairs = new HashSet<PaillierPrivateKey>();
        while(keypairs.size() < repeats) {
            PaillierPrivateKey privateKey = PaillierPrivateKey.create(1024);
            if(keypairs.contains(privateKey))
            	fail("Non unique keypair.");
            keypairs.add(privateKey);
        }
    }

    @Category(SlowTests.class)
    @Test
    public void testKeyUniqueness2104() throws Exception {
        int repeats = 100;
        HashSet<PaillierPrivateKey> keypairs = new HashSet<PaillierPrivateKey>();
        while(keypairs.size() < repeats) {
            PaillierPrivateKey privateKey = PaillierPrivateKey.create(2104);
            if(keypairs.contains(privateKey))
                fail("Non unique keypair.");
            keypairs.add(privateKey);
        }
    }
}
