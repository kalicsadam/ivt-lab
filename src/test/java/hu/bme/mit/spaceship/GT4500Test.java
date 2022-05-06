package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import java.nio.ShortBuffer;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore primaryStoreMock;
  private TorpedoStore secondaryStoreMock;

  @BeforeEach
  public void init(){
    primaryStoreMock = mock(TorpedoStore.class);
    secondaryStoreMock = mock(TorpedoStore.class);
    this.ship = new GT4500(primaryStoreMock, secondaryStoreMock);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(primaryStoreMock.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);

    verify(primaryStoreMock, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void check_primary_fired_first() {
    // Arrange
    when(primaryStoreMock.fire(1)).thenReturn(true);
    when(secondaryStoreMock.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);

    verify(primaryStoreMock, times(1)).fire(1);
    verify(secondaryStoreMock, times(0)).fire(1);
  }

  @Test
  public void check_altering_firing() {
    ship.fireTorpedo(FiringMode.SINGLE);
    verify(primaryStoreMock, times(1)).fire(1);
    verify(secondaryStoreMock, times(0)).fire(1);

    ship.fireTorpedo(FiringMode.SINGLE);
    verify(primaryStoreMock, times(1)).fire(1);
    verify(secondaryStoreMock, times(1)).fire(1);

    ship.fireTorpedo(FiringMode.SINGLE);
    verify(primaryStoreMock, times(2)).fire(1);
    verify(secondaryStoreMock, times(1)).fire(1);

    ship.fireTorpedo(FiringMode.SINGLE);
    verify(primaryStoreMock, times(2)).fire(1);
    verify(secondaryStoreMock, times(2)).fire(1);
  }

  @Test
  public void check_if_primary_isempty_fire_secondary() {
    when(primaryStoreMock.isEmpty()).thenReturn(true);

    ship.fireTorpedo(FiringMode.SINGLE);

    verify(primaryStoreMock, times(0)).fire(1);
    verify(secondaryStoreMock, times(1)).fire(1);
  }

 @Test
 public void check_if_scondary_isempty_fire_primary() {
   when(secondaryStoreMock.isEmpty()).thenReturn(true);

   ship.fireTorpedo(FiringMode.SINGLE);
   ship.fireTorpedo(FiringMode.SINGLE);

   verify(primaryStoreMock, times(2)).fire(1);
   verify(secondaryStoreMock, times(0)).fire(1);
 }

  @Test
  public void check_failure_doesnt_fire_other() {
    when(primaryStoreMock.fire(1)).thenReturn(false);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(false, result);

    verify(secondaryStoreMock, times(0)).fire(1);
  }

 @Test
 public void check_both_empty() {
  when(primaryStoreMock.isEmpty()).thenReturn(true);
  when(secondaryStoreMock.isEmpty()).thenReturn(true);

  boolean result = ship.fireTorpedo(FiringMode.SINGLE);

  assertEquals(false, result);
 }  

  @Test
  public void fire_laser() {
    boolean result = ship.fireLaser(FiringMode.SINGLE);
    
    assertEquals(true, result);
  } 


}
