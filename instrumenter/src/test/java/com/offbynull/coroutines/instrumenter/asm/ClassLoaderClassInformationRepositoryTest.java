package com.offbynull.coroutines.instrumenter.asm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public final class ClassLoaderClassInformationRepositoryTest {
    
    private ClassLoaderClassInformationRepository repo;
    
    @BeforeEach
    public void setUp() {
        ClassLoader classLoader = getClass().getClassLoader();
        repo = new ClassLoaderClassInformationRepository(classLoader);
    }

    @Test
    public void mustGetClassInformationForInteger() {
        ClassInformation info = repo.getInformation("java/lang/Integer");
        
        assertEquals("java/lang/Number", info.getSuperClassName());
        assertEquals(1, info.getInterfaces().size());
        assertTrue(info.getInterfaces().contains("java/lang/Comparable"));
        assertFalse(info.isInterface());
    }
    
    @Test
    public void mustGetClassInformationForBoolean() {
        ClassInformation info = repo.getInformation("java/lang/Boolean");
        
        assertEquals("java/lang/Object", info.getSuperClassName());
        assertEquals(2, info.getInterfaces().size());
        assertTrue(info.getInterfaces().contains("java/lang/Comparable"));
        assertTrue(info.getInterfaces().contains("java/io/Serializable"));
        assertFalse(info.isInterface());
    }
    
    @Test
    public void mustGetClassInformationForObject() {
        ClassInformation info = repo.getInformation("java/lang/Object");
        
        assertNull(info.getSuperClassName());
        assertTrue(info.getInterfaces().isEmpty());
        assertFalse(info.isInterface());
    }

    @Test
    public void mustGetClassInformationForRunnableFuture() {
        ClassInformation info = repo.getInformation("java/util/concurrent/RunnableFuture");
        
        assertEquals("java/lang/Object", info.getSuperClassName());
        assertEquals(2, info.getInterfaces().size());
        assertTrue(info.getInterfaces().contains("java/lang/Runnable"));
        assertTrue(info.getInterfaces().contains("java/util/concurrent/Future"));
        assertTrue(info.isInterface());
    }

    @Test
    public void mustFailToGetClassInformationForUnknownClass() {
        ClassInformation info = repo.getInformation("2huowhf9w37fy9fhnwfwfwefasef");
        
        assertNull(info);
    }
    
}
