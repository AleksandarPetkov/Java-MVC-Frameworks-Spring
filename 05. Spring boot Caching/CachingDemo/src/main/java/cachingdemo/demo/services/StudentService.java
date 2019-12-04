package cachingdemo.demo.services;

import cachingdemo.demo.models.Student;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    //This annotation is enabling caching in this particular method and cache name is student.
    @Cacheable("student")
    public Student getStudentById(String id){
        try
        {
//          In the getStudentByID() method we have an intentional 5 seconds delay using Thread.sleep(1000*5).
//          This is just to understand whether response is coming from cache or real backend.
      //    The Response of the First call will be 5 sec, then subsequent responses of the same url will be FASTER
     //     Because after the first call student is caching!
            System.out.println("Going to sleep for 5 Secs.. to simulate backend call.");
            Thread.sleep(1000*5);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        return new Student("111","Alex" ,"Petkov");
    }
}
