/**
 * DocumentMasterSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1  Built on : Aug 31, 2011 (12:22:40 CEST)
 */
package gutenberg.blocs;
import gutenberg.blocs.WriteTex; 
import gutenberg.blocs.WriteTexResponse; 
import gutenberg.blocs.CompileTex;
import gutenberg.blocs.CompileTexResponse; 

import gutenberg.workers.Config;
import gutenberg.workers.FrontDesk;
import gutenberg.workers.Locker;
import gutenberg.workers.Mint;
import gutenberg.workers.Vault;

/**
 * DocumentMasterSkeleton java skeleton for the axisService
 */
public class DocumentMasterSkeleton implements DocumentMasterSkeletonInterface {

    @Override
    public gutenberg.blocs.TagQuestionResponse tagQuestion(
            gutenberg.blocs.TagQuestion tagQuestion) {
        Vault vault = null;
        Config config = null;
        ResponseType response = new ResponseType();
        try {
            config = new Config();
            vault = new Vault(config);
            QuestionTagsType tags = tagQuestion.getTagQuestion();
            response.setManifest(vault.tagQuestion(tags));
        } catch (Exception e) {
            e.printStackTrace();
            response.setError(e.getMessage());
        }
        TagQuestionResponse tagQuestionResponse = new TagQuestionResponse();
        tagQuestionResponse.setTagQuestionResponse(response);
        return tagQuestionResponse;
    }

    @Override
    public gutenberg.blocs.AnnotateScanResponse annotateScan(
            gutenberg.blocs.AnnotateScan annotateScan) {
        Locker locker = null;
        Config config = null;
        ResponseType response = new ResponseType();
        try {
            config = new Config();
            locker = new Locker(config);
            String scanId = annotateScan.getAnnotateScan().getScanId();
            PointType[] points = annotateScan.getAnnotateScan()
                    .getCoordinates();
            response.setManifest(locker.annotate(scanId, points));
        } catch (Exception e) {
            e.printStackTrace();
            response.setError(e.getMessage());
        }
        AnnotateScanResponse annotateScanResponse = new AnnotateScanResponse();
        annotateScanResponse.setAnnotateScanResponse(response);
        return annotateScanResponse;
    }

    @Override
    public UndoAnnotateResponse undoAnnotate(UndoAnnotate undoAnnotate) {
        Locker locker = null;
        Config config = null;
        ResponseType response = new ResponseType();
        try {
            config = new Config();
            locker = new Locker(config);
            String scanId = undoAnnotate.getUndoAnnotate().getScanId();
            response.setManifest(locker.undoAnnotate(scanId));
        } catch (Exception e) {
            e.printStackTrace();
            response.setError(e.getMessage());
        }
        UndoAnnotateResponse undoAnnotateResponse = new UndoAnnotateResponse(); 
        undoAnnotateResponse.setUndoAnnotateResponse(response);
        return undoAnnotateResponse;
    }

    @Override
    public RotateScanResponse rotateScan(RotateScan rotateScan) {
        Locker locker = null;
        Config config = null;
        ResponseType response = new ResponseType();
        try {
            config = new Config();
            locker = new Locker(config);
            String scanId = rotateScan.getRotateScan().getScanId();
            response.setManifest(locker.rotate(scanId));
        } catch (Exception e) {
            e.printStackTrace();
            response.setError(e.getMessage());
        }
        RotateScanResponse rotateScanResponse = new RotateScanResponse();
        rotateScanResponse.setRotateScanResponse(response);
        return rotateScanResponse;
    }
    
    @Override
    public gutenberg.blocs.CreateQuestionResponse createQuestion(
            gutenberg.blocs.CreateQuestion createQuestion) {
        Config config = null;
        ResponseType response = new ResponseType();
        try {
            String quizMasterId = createQuestion.getCreateQuestion();
            config = new Config();
            Vault vault = new Vault(config);
            response.setManifest(vault.createQuestion(quizMasterId));
        } catch (Exception e) {
            e.printStackTrace();
            response.setError(e.getMessage());
        }
        CreateQuestionResponse createQuestionResponse = new CreateQuestionResponse();
        createQuestionResponse.setCreateQuestionResponse(response);
        return createQuestionResponse;
    }

    @Override
    public gutenberg.blocs.BuildQuizResponse buildQuiz(
            gutenberg.blocs.BuildQuiz buildQuiz) {
        Config config = null;
        Mint mint = null;
        ResponseType response = new ResponseType();
        try {
            config = new Config();
            mint = new Mint(config);
            QuizType quiz = buildQuiz.getBuildQuiz();
            response.setManifest(mint.generate(quiz));
        } catch (Exception e) {
            e.printStackTrace();
            response.setError(e.getMessage());
        }
        BuildQuizResponse buildQuizResponse = new BuildQuizResponse();
        buildQuizResponse.setBuildQuizResponse(response);
        return buildQuizResponse;
    }

    @Override
    public gutenberg.blocs.AssignQuizResponse assignQuiz(
            gutenberg.blocs.AssignQuiz assignQuiz) {
        Config config = null;
        ResponseType response = new ResponseType();
        try {
            config = new Config();
            Mint mint = new Mint(config);
            AssignmentType assignment = assignQuiz.getAssignQuiz();
            response.setManifest(mint.generate(assignment));
        } catch (Exception e) {
            e.printStackTrace();
            response.setError(e.getMessage());
        }
        AssignQuizResponse assignQuizResponse = new AssignQuizResponse();
        assignQuizResponse.setAssignQuizResponse(response);
        return assignQuizResponse;
    }

    @Override
    public GenerateStudentRosterResponse generateStudentRoster(
            GenerateStudentRoster generateStudentRoster) {
        Config config = null;
        FrontDesk frontdesk = null;
        ResponseType response = new ResponseType();
        try {
            config = new Config();
            frontdesk = new FrontDesk(config);
            StudentGroupType studentGroup = generateStudentRoster.getGenerateStudentRoster();
            response.setManifest(frontdesk.generateStudentRoster(studentGroup));
        } catch (Exception e) {
            e.printStackTrace();
            response.setError(e.getMessage());
        }
        GenerateStudentRosterResponse
            generateStudentRosterResponse = new GenerateStudentRosterResponse();
        generateStudentRosterResponse.setGenerateStudentRosterResponse(response);
        return generateStudentRosterResponse;
    }

    @Override
    public gutenberg.blocs.GenerateQuizReportResponse generateQuizReport(
            gutenberg.blocs.GenerateQuizReport generateQuizReport) {
        Config config = null;
        FrontDesk frontdesk = null;
        ResponseType response = new ResponseType();
        try {
            config = new Config();
            frontdesk = new FrontDesk(config);
            StudentGroupType studentGroup = generateQuizReport.getGenerateQuizReport();
            response.setManifest(frontdesk.generateQuizReport(studentGroup));
        } catch (Exception e) {
            e.printStackTrace();
            response.setError(e.getMessage());
        }
        GenerateQuizReportResponse
            generateQuizReportResponse = new GenerateQuizReportResponse();
        generateQuizReportResponse.setGenerateQuizReportResponse(response);
        return generateQuizReportResponse;

    }

    @Override
    public PrepTestResponse prepTest(
            PrepTest prepTest) {
        Config config = null;
        Mint mint = null;
        ResponseType response = new ResponseType();
        try {
            config = new Config();
            mint = new Mint(config);
            AssignmentType assignment = prepTest.getPrepTest();
            response.setManifest(mint.prepTest(assignment));
        } catch (Exception e) {
            e.printStackTrace();
            response.setError(e.getMessage());
        }
        PrepTestResponse 
            prepTestResponse = new PrepTestResponse();
        prepTestResponse.setPrepTestResponse(response);
        return prepTestResponse;
    }

    @Override
    public UploadSuggestionResponse uploadSuggestion(
            UploadSuggestion uploadSuggestion) {
        Locker locker = null;
        Config config = null;
        ResponseType response = new ResponseType();
        try {
            config = new Config();
            locker = new Locker(config);
            SuggestionType suggestion = uploadSuggestion.getUploadSuggestion();            
            response.setManifest(locker.uploadSuggestion(suggestion.getSignature(),
                    suggestion.getTeacher(), suggestion.getContent()));
        } catch (Exception e) {
            e.printStackTrace();
            response.setError(e.getMessage());
        }
        UploadSuggestionResponse uploadSuggestionResponse = new UploadSuggestionResponse();
        uploadSuggestionResponse.setUploadSuggestionResponse(response);
        return uploadSuggestionResponse;
    }

    @Override
    public FetchUnresolvedScansResponse fetchUnresolvedScans(
        FetchUnresolvedScans fetchUnresolvedScans) {
        Locker locker = null;
        Config config = null;
        ResponseType response = new ResponseType();
        try {
            config = new Config();
            locker = new Locker(config);
            WorkRequestType workRequest = fetchUnresolvedScans.
                getFetchUnresolvedScans();
            response.setManifest(locker.fetchUnresolved(
                workRequest.getGrader(), workRequest.getMaxQuantity()));
        } catch (Exception e) {
            e.printStackTrace();
            response.setError(e.getMessage());
        }
        FetchUnresolvedScansResponse fetchUnresolvedScansResponse = new FetchUnresolvedScansResponse();
        fetchUnresolvedScansResponse.setFetchUnresolvedScansResponse(response);
        return fetchUnresolvedScansResponse;
    }

    @Override
    public ResolveScanResponse resolveScan(ResolveScan resolveScan) {
        Locker locker = null;
        Config config = null;
        ResponseType response = new ResponseType();
        try {
            config = new Config();
            locker = new Locker(config);
            EntryType scan = resolveScan.getResolveScan();
            response.setManifest(locker.resolveScan(scan.getId(), scan.getValue()));
        } catch (Exception e) {
            e.printStackTrace();
            response.setError(e.getMessage());
        }
        ResolveScanResponse resolveScanResponse = new ResolveScanResponse();
        resolveScanResponse.setResolveScanResponse(response);
        return resolveScanResponse;
    }

    @Override
    public WriteTexResponse writeTex(WriteTex flags){
      ResponseType resp = new ResponseType() ;
      TexFlags f = flags.getWriteTex() ;
      
      try { 
        Config cnfg = new Config() ;
        Mint mint = new Mint(cnfg) ;
          
        if (mint.createTex(f) == 0){
          ManifestType m = new ManifestType() ;
          m.setRoot(f.getTarget()) ;
          resp.setManifest(m) ;
        } else {
          resp.setError("(" + f.getTarget() + ") ---> TeX writing failed") ;
        }
      } catch (Exception e) {
        e.printStackTrace() ;
        resp.setError(e.getMessage()) ;
      }
      WriteTexResponse r = new WriteTexResponse() ;
      r.setWriteTexResponse(resp) ;
      return r ;
    }

    @Override
    public CompileTexResponse compileTex(CompileTex flags){
      ResponseType resp = new ResponseType() ;
      MkFlags f = flags.getCompileTex() ;
      try { 
        Config cnfg = new Config() ;
        Mint mint = new Mint(cnfg) ;
        if (mint.compileTex(f) == 0){
          ManifestType m = new ManifestType() ;
          m.setRoot(f.getPath()) ;
          resp.setManifest(m) ;
        } else {
          resp.setError("(" + f.getPath() + ") --> compilation failed") ;
        }
      } catch (Exception e) {
        e.printStackTrace() ;
        resp.setError(e.getMessage()) ;
      }
      CompileTexResponse r = new CompileTexResponse() ;
      r.setCompileTexResponse(resp) ;
      return r ;
    }

    @Override
    public ErrorOutResponse errorOut(ErrorOut path){
      ResponseType resp = new ResponseType();
      MkFlags f = path.getErrorOut() ;
      try { 
        Config cnfg = new Config() ;
        Mint mint = new Mint(cnfg) ;
        String lnk = mint.errorOut(f) ;
        if (lnk != null){
          ManifestType m = new ManifestType() ;
          m.setRoot(lnk) ;
          resp.setManifest(m) ;
        } else {
          resp.setError("(" + f.getPath() + ") --> compilation failed") ;
        }
      } catch (Exception e) {
        e.printStackTrace() ;
        resp.setError(e.getMessage()) ;
      }
      ErrorOutResponse r = new ErrorOutResponse() ;
      r.setErrorOutResponse(resp) ;
      return r ;
    }
}
