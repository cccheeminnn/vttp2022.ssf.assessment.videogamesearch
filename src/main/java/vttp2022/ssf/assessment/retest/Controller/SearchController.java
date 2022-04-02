package vttp2022.ssf.assessment.retest.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp2022.ssf.assessment.retest.Model.Game;
import vttp2022.ssf.assessment.retest.Service.SearchService;

@Controller
@RequestMapping(path="")
public class SearchController {

    @Autowired
    private SearchService searchSvc;
    
    @GetMapping(path="")
    public String getSearchPage() {
        return "searchPage";
    }
    
    @GetMapping(path="/search")
    public String getSearchResults(Model m, @RequestParam String searchVal, @RequestParam(required = false, defaultValue = "10") String display) {
        
        List<Game> gameResults = searchSvc.search(searchVal, Integer.parseInt(display));

        m.addAttribute("gameResults", gameResults);
        return "resultPage";
    }
}
