
/**
 * DocumentMasterSkeletonInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1  Built on : Aug 31, 2011 (12:22:40 CEST)
 */
    package gutenberg.blocs;
    /**
     *  DocumentMasterSkeletonInterface java skeleton interface for the axisService
     */
    public interface DocumentMasterSkeletonInterface {
     
         
        /**
         * Auto generated method signature
         * 
                                    * @param tagQuestion
         */

        
                public gutenberg.blocs.TagQuestionResponse tagQuestion
                (
                  gutenberg.blocs.TagQuestion tagQuestion
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param createQuestion
         */

        
                public gutenberg.blocs.CreateQuestionResponse createQuestion
                (
                  gutenberg.blocs.CreateQuestion createQuestion
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param annotateScan
         */

        
                public gutenberg.blocs.AnnotateScanResponse annotateScan
                (
                  gutenberg.blocs.AnnotateScan annotateScan
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param buildQuiz
         */

        
                public gutenberg.blocs.BuildQuizResponse buildQuiz
                (
                  gutenberg.blocs.BuildQuiz buildQuiz
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param generateQuizReport
         */

        
                public gutenberg.blocs.GenerateQuizReportResponse generateQuizReport
                (
                  gutenberg.blocs.GenerateQuizReport generateQuizReport
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param undoAnnotate
         */

        
                public gutenberg.blocs.UndoAnnotateResponse undoAnnotate
                (
                  gutenberg.blocs.UndoAnnotate undoAnnotate
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param generateStudentRoster
         */

        
                public gutenberg.blocs.GenerateStudentRosterResponse generateStudentRoster
                (
                  gutenberg.blocs.GenerateStudentRoster generateStudentRoster
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param generateSuggestionForm
         */

        
                public gutenberg.blocs.GenerateSuggestionFormResponse generateSuggestionForm
                (
                  gutenberg.blocs.GenerateSuggestionForm generateSuggestionForm
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param rotateScan
         */

        
                public gutenberg.blocs.RotateScanResponse rotateScan
                (
                  gutenberg.blocs.RotateScan rotateScan
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param receiveScans
         */

        
                public gutenberg.blocs.ReceiveScansResponse receiveScans
                (
                  gutenberg.blocs.ReceiveScans receiveScans
                 )
            ;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param assignQuiz
         */

        
                public gutenberg.blocs.AssignQuizResponse assignQuiz
                (
                  gutenberg.blocs.AssignQuiz assignQuiz
                 )
            ;
        
                /**
                 * Auto generated method signature
                 * 
                                            * @param assignQuiz
                 */

                
                public gutenberg.blocs.PrepTestResponse prepTest
                (
                  gutenberg.blocs.PrepTest prepTest
                 )
            ;
         }
    