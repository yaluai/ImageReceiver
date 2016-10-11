///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            RUI YIN
// Files:            Receiver.java"
//"PacketLinkedList.java"
//"PacketLinkedListIterator.java"
//"BadImageHeaderException.java"
//"BadImageContentException.java"
// Semester:         (course) Spring 2016
//
// Author:           RUI YIN
// Email:            RYIN5@WISC.EDU
// CS Login:         RYIN
// Lecturer's Name:  DEB DEPPLER
// Lab Section:      2
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
//                   CHECK ASSIGNMENT PAGE TO see IF PAIR-PROGRAMMING IS ALLOWED
//                   If pair programming is allowed:
//                   1. Read PAIR-PROGRAMMING policy (in cs302 policy) 
//                   2. choose a partner wisely
//                   3. REGISTER THE TEAM BEFORE YOU WORK TOGETHER 
//                      a. one partner creates the team
//                      b. the other partner must join the team
//                   4. complete this section for each program file.
//
// Pair Partner:     (name of your pair programming partner)
// Email:            (email address of your programming partner)
// CS Login:         (partner's login name)
// Lecturer's Name:  (name of your partner's lecturer)
// Lab Section:      (your partner's lab section number)
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   must fully acknowledge and credit those sources of help.
//                   Instructors and TAs do not have to be credited here,
//                   but tutors, roommates, relatives, strangers, etc do.
//
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
//
// Online sources:   avoid web searches to solve your problems, but if you do
//                   search, be sure to include Web URLs and description of 
//                   of any information you find.
//////////////////////////// 80 columns wide //////////////////////////////////
public class BadImageHeaderException extends RuntimeException {
    /**
     * @param s the error message
     */
    public BadImageHeaderException(String s) {
        super(s);
    }

    /**
     * Constructs a BadImageHeaderException
     */
    public BadImageHeaderException() {
        super("The image is broken due to a damaged header.");
    }
}
