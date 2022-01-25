package com.sbrf.reboot.equalshashcode;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class EqualsHashCodeTest {

    class Car {
        String model;
        String color;
        Calendar releaseDate;
        int maxSpeed;

        @Override
        public boolean equals(Object o) {
            // Is the same object
            if (o == this) return true;

            // Null-checking + The object class must be the same as the current one
            if (o == null || getClass() != o.getClass()) return false;

            Car car = (Car) o;

            // Checking the fields
            if (maxSpeed != car.maxSpeed) return false;
            if (!Objects.equals(model, car.model)) return false;
            if (!Objects.equals(color, car.color)) return false;
            if (!Objects.equals(releaseDate, car.releaseDate)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = model != null ? model.hashCode() : 0;
            result = 31 * result + (color != null ? color.hashCode() : 0);
            result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
            result = 31 * result + maxSpeed;
            return result;
        }
    }

    @Test
    public void assertTrueEquals() {
        Car car1 = new Car();
        car1.model = "Mercedes";
        car1.color = "black";
        car1.releaseDate = new GregorianCalendar(2020, Calendar.JANUARY, 25);
        car1.maxSpeed = 10;

        Car car2 = new Car();
        car2.model = "Mercedes";
        car2.color = "black";
        car2.releaseDate = new GregorianCalendar(2020, Calendar.JANUARY, 25);
        car2.maxSpeed = 10;

        assertTrue(car1.equals(car2));
    }

    @Test
    public void assertFalseEquals() {
        Car car1 = new Car();
        car1.model = "Mercedes";
        car1.color = "black";
        car1.releaseDate = new GregorianCalendar(2020, Calendar.JANUARY, 25);
        car1.maxSpeed = 10;

        Car car2 = new Car();
        car2.model = "Audi";
        car2.color = "white";
        car2.releaseDate = new GregorianCalendar(2017, Calendar.JANUARY, 25);
        car2.maxSpeed = 10;

        assertFalse(car1.equals(car2));
    }

    @Test
    public void successEqualsHashCode() {
        Car car1 = new Car();
        car1.model = "Mercedes";
        car1.color = "black";
        car1.releaseDate = new GregorianCalendar(2020, Calendar.JANUARY, 25);
        car1.maxSpeed = 10;

        Car car2 = new Car();
        car2.model = "Mercedes";
        car2.color = "black";
        car2.releaseDate = new GregorianCalendar(2020, Calendar.JANUARY, 25);
        car2.maxSpeed = 10;

        assertEquals(car1.hashCode(), car2.hashCode());
    }

    @Test
    public void failEqualsHashCode() {
        Car car1 = new Car();
        car1.model = "Mercedes";
        car1.color = "black";
        car1.releaseDate = new GregorianCalendar(2020, Calendar.JANUARY, 25);
        car1.maxSpeed = 10;

        Car car2 = new Car();
        car2.model = "Audi";
        car2.color = "white";
        car2.releaseDate = new GregorianCalendar(2017, Calendar.JANUARY, 25);
        car2.maxSpeed = 10;

        assertNotEquals(car1.hashCode(), car2.hashCode());
    }
}
