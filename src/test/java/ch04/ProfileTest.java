package ch04;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProfileTest {

    @Test
    public void matches() {
        Profile profile = new Profile("Bull Hockey, Inc.");
        Question question = new BooleanQuestion(1, "Got milk?");

        // answers false when must-match criteria not met
        profile.add(new Answer(question, Bool.FALSE));
        Criteria criteria = new Criteria();
        criteria.add(
                new Criterion(new Answer(question, Bool.TRUE), Weight.MustMatch));
        assertFalse(profile.matches(criteria));

        // answers true for any don't care criteria
        profile.add(new Answer(question, Bool.FALSE));
        criteria = new Criteria();
        criteria.add(
                new Criterion(new Answer(question, Bool.TRUE), Weight.DontCare));
        assertTrue(profile.matches(criteria));
    }
}
