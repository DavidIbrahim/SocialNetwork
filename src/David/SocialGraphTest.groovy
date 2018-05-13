package David


import GraphClasses.SocialGraph
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4



@RunWith(JUnit4.class)
class SocialGraphTest extends GroovyTestCase {
    static SocialGraph socialGraph


    @Test
    void testTheGraphData() {

        assertEquals(1000, socialGraph.numberOfAccounts)
        assertEquals(74, socialGraph.getAccount("Noah").friends.size())
        assertTrue(socialGraph.getAccount("James").friends.contains("Ernesto"))
        assertArrayEquals(["Harrison","Damari","Ayaan"].toArray(),socialGraph.getAccount("Stephen").friends.toArray())
    }




    @Test
    void testSuggestingFriends1() {
        String[] expectedSuggestions = ["Stephen","Adonis","Tyrone","Harry","Bryson","Moses","Yusuf"
            ,"Axton","Gabriel","Carlos","Aarush"
            ,"Daniel","Beckett","Atlas","Dylan"
            ,"Lyric","Camilo","Brock","Riaan"
        ]

        ArrayList<String> suggestedFriends = socialGraph.getAccount("Stephen").getSuggestedFriends(socialGraph,10)
        for (int i = 0; i <suggestedFriends.size() ; i++) {

            try {
                assertTrue(expectedSuggestions.contains(suggestedFriends[i]))
            }
            catch (AssertionError x){
                println "Didn't Find: " +suggestedFriends[i]+" in the suggestions"
                throw x
            }
        }

    }

    @Test
    void testSuggestingFriends2() {
        String[] expectedSuggestions = ['Thatcher', 'Bruce', 'Marco',
                                        'Sylas', 'Joshua', 'Damon', 'Jaxton',
                                        'Paul', 'Skyler', 'Moshe', 'Zackary', 'Orlando',
                                        'Riaan', 'Uriel', 'Seamus', 'Bruno', 'Alvaro', 'Judson',
                                        'Steve', 'Maximus', 'Javier', 'Walker', 'Fabian',
                                        'Zaire', 'Duke', 'Isaac', 'Camilo', 'Henry']

        ArrayList<String> suggestedFriends = socialGraph.getAccount("Dalton").getSuggestedFriends(socialGraph,10)
        for (int i = 0; i <suggestedFriends.size() ; i++) {

            try {
                assertTrue(expectedSuggestions.contains(suggestedFriends[i]))
            }
            catch (AssertionError x){
                println "Didn't Find: " +suggestedFriends[i]+" in the suggestions"
                throw x
            }
        }



    }



    @Test
    void testHashTagsSearching() {
        assertEquals(8, SocialGraph.hashTagSearch("#beautiful").size())
        assertEquals(11, SocialGraph.hashTagSearch("#cute").size())
        assertEquals(7, SocialGraph.hashTagSearch("#hot").size())
        assertEquals(5, SocialGraph.hashTagSearch("#goals").size())
        assertTrue(SocialGraph.hashTagSearch("#bf").toString().contains(" I forgot to work out today. That's 5 years in a row! #bf"))
        assertTrue(SocialGraph.hashTagSearch("#bf").toString().contains(" If money grew on trees.. Congress would actually care about the environment. #bf"))
        assertTrue(SocialGraph.hashTagSearch("#omg").toString().contains(" Currently helping my son search for his chocolate that I ate last night. #omg"))
    }

    @BeforeClass
    static void setUpTheGraph() {
        socialGraph = new SocialGraph()
        QueryExecutor.executeQueryFile(socialGraph, "res/Test1/createAccTest1.txt")
        QueryExecutor.executeQueryFile(socialGraph, "res/Test1/addFriendsTest1.txt")
        QueryExecutor.executeQueryFile(socialGraph,"res/Test1/makePostsCommandTest1.txt");
        println("setupTheGraphFinished")
    }


}
