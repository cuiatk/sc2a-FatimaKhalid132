/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2014-02-17T10:00:00Z");
    private static final Instant d4 = Instant.parse("2018-02-17T11:00:00Z");
    private static final Instant d5 = Instant.parse("2017-02-17T10:00:00Z");
 
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "abc", "@junit tests in #software_construction", d2);
    private static final Tweet tweet4 = new Tweet(3, "we", "@junit tests in #software_construction", d3);
    private static final Tweet tweet5 = new Tweet(3, "cu", "@junit tests in #software_construction", d4);
    private static final Tweet tweet6 = new Tweet(3, "sc", "@junit tests in #software_construction", d5);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
  //Tests for getTimespan()
    @Test
    // covers tweets.size() = 1
    public void testGetTimespan_OneTweet() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
        
        assertEquals("Expected start == end",
                timespan.getStart(), timespan.getEnd());
        assertEquals("Expected start == d1",
                timespan.getStart(), d1);
    } 
    @Test
    // covers tweets.size > 1
    public void testGetTimespan_TwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
         
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    @Test
    // covers tweets.size > 1
    //        tweets having the same timestamp
    public void testGetTimespan_SameTimestamp() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet2, tweet3));
        
        assertEquals("Expected start == end",
                timespan.getStart(), timespan.getEnd());
        assertEquals("Expected start == d2",
                timespan.getStart(), d2);
    }
    
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
   
    @Test
    public void testGetMentionedUsersoneMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1,tweet2,tweet3));
        
        assertTrue("Expected one user mentioned", mentionedUsers.contains(mentionedUsers));
    }

    @Test
    public void testGetMentionedUsersOneMentionOneTweet() {   

        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet4));
        Set<String> mentionedUsersLowerCase = new HashSet<>();

        for (String mentionedUser : mentionedUsers) {
            mentionedUsersLowerCase.add(mentionedUser.toLowerCase());
        }
        assertTrue(mentionedUsersLowerCase.contains("test1"));
    }

    @Test
    public void testGetMentionedUsersTwoMentionOneTweet() {   

        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet5));
        Set<String> mentionedUsersLowerCase = new HashSet<>();

        for (String mentionedUser : mentionedUsers) {
            mentionedUsersLowerCase.add(mentionedUser.toLowerCase());
        }

        assertTrue(mentionedUsersLowerCase.containsAll(Arrays.asList("test1", "test2")));
    }


    @Test
    public void testGetMentionedUsersTwoeMentionOneTweetrepeateduser() {         

        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet6));
        Set<String> mentionedUsersLowerCase = new HashSet<>();

        for (String mentionedUser : mentionedUsers) {
            mentionedUsersLowerCase.add(mentionedUser.toLowerCase());
        }
        assertTrue(mentionedUsersLowerCase.contains("test1"));
    }    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}
