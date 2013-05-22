
/**
 * DocumentMasterMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1  Built on : Aug 31, 2011 (12:22:40 CEST)
 */
        package gutenberg.blocs;

        /**
        *  DocumentMasterMessageReceiverInOut message receiver
        */

        public class DocumentMasterMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutMessageReceiver{


        public void invokeBusinessLogic(org.apache.axis2.context.MessageContext msgContext, org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault{

        try {

        // get the implementation class for the Web Service
        Object obj = getTheImplementationObject(msgContext);

        DocumentMasterSkeletonInterface skel = (DocumentMasterSkeletonInterface)obj;
        //Out Envelop
        org.apache.axiom.soap.SOAPEnvelope envelope = null;
        //Find the axisOperation that has been set by the Dispatch phase.
        org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext().getAxisOperation();
        if (op == null) {
        throw new org.apache.axis2.AxisFault("Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
        }

        java.lang.String methodName;
        if((op.getName() != null) && ((methodName = org.apache.axis2.util.JavaUtils.xmlNameToJavaIdentifier(op.getName().getLocalPart())) != null)){


        

            if("tagQuestion".equals(methodName)){
                
                gutenberg.blocs.TagQuestionResponse tagQuestionResponse31 = null;
	                        gutenberg.blocs.TagQuestion wrappedParam =
                                                             (gutenberg.blocs.TagQuestion)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    gutenberg.blocs.TagQuestion.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               tagQuestionResponse31 =
                                                   
                                                   
                                                         skel.tagQuestion(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), tagQuestionResponse31, false, new javax.xml.namespace.QName("http://gutenberg/blocs",
                                                    "tagQuestion"));
                                    } else 

            if("uploadSuggestion".equals(methodName)){
                
                gutenberg.blocs.UploadSuggestionResponse uploadSuggestionResponse33 = null;
	                        gutenberg.blocs.UploadSuggestion wrappedParam =
                                                             (gutenberg.blocs.UploadSuggestion)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    gutenberg.blocs.UploadSuggestion.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               uploadSuggestionResponse33 =
                                                   
                                                   
                                                         skel.uploadSuggestion(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), uploadSuggestionResponse33, false, new javax.xml.namespace.QName("http://gutenberg/blocs",
                                                    "uploadSuggestion"));
                                    } else 

            if("fetchUnresolvedScans".equals(methodName)){
                
                gutenberg.blocs.FetchUnresolvedScansResponse fetchUnresolvedScansResponse35 = null;
	                        gutenberg.blocs.FetchUnresolvedScans wrappedParam =
                                                             (gutenberg.blocs.FetchUnresolvedScans)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    gutenberg.blocs.FetchUnresolvedScans.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               fetchUnresolvedScansResponse35 =
                                                   
                                                   
                                                         skel.fetchUnresolvedScans(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), fetchUnresolvedScansResponse35, false, new javax.xml.namespace.QName("http://gutenberg/blocs",
                                                    "fetchUnresolvedScans"));
                                    } else 

            if("resolveScan".equals(methodName)){
                
                gutenberg.blocs.ResolveScanResponse resolveScanResponse37 = null;
	                        gutenberg.blocs.ResolveScan wrappedParam =
                                                             (gutenberg.blocs.ResolveScan)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    gutenberg.blocs.ResolveScan.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               resolveScanResponse37 =
                                                   
                                                   
                                                         skel.resolveScan(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), resolveScanResponse37, false, new javax.xml.namespace.QName("http://gutenberg/blocs",
                                                    "resolveScan"));
                                    } else 

            if("createQuestion".equals(methodName)){
                
                gutenberg.blocs.CreateQuestionResponse createQuestionResponse39 = null;
	                        gutenberg.blocs.CreateQuestion wrappedParam =
                                                             (gutenberg.blocs.CreateQuestion)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    gutenberg.blocs.CreateQuestion.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createQuestionResponse39 =
                                                   
                                                   
                                                         skel.createQuestion(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createQuestionResponse39, false, new javax.xml.namespace.QName("http://gutenberg/blocs",
                                                    "createQuestion"));
                                    } else 

            if("prepTest".equals(methodName)){
                
                gutenberg.blocs.PrepTestResponse prepTestResponse41 = null;
	                        gutenberg.blocs.PrepTest wrappedParam =
                                                             (gutenberg.blocs.PrepTest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    gutenberg.blocs.PrepTest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               prepTestResponse41 =
                                                   
                                                   
                                                         skel.prepTest(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), prepTestResponse41, false, new javax.xml.namespace.QName("http://gutenberg/blocs",
                                                    "prepTest"));
                                    } else 

            if("generateStudentCode".equals(methodName)){
                
                gutenberg.blocs.GenerateStudentCodeResponse generateStudentCodeResponse43 = null;
	                        gutenberg.blocs.GenerateStudentCode wrappedParam =
                                                             (gutenberg.blocs.GenerateStudentCode)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    gutenberg.blocs.GenerateStudentCode.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               generateStudentCodeResponse43 =
                                                   
                                                   
                                                         skel.generateStudentCode(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), generateStudentCodeResponse43, false, new javax.xml.namespace.QName("http://gutenberg/blocs",
                                                    "generateStudentCode"));
                                    } else 

            if("annotateScan".equals(methodName)){
                
                gutenberg.blocs.AnnotateScanResponse annotateScanResponse45 = null;
	                        gutenberg.blocs.AnnotateScan wrappedParam =
                                                             (gutenberg.blocs.AnnotateScan)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    gutenberg.blocs.AnnotateScan.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               annotateScanResponse45 =
                                                   
                                                   
                                                         skel.annotateScan(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), annotateScanResponse45, false, new javax.xml.namespace.QName("http://gutenberg/blocs",
                                                    "annotateScan"));
                                    } else 

            if("buildQuiz".equals(methodName)){
                
                gutenberg.blocs.BuildQuizResponse buildQuizResponse47 = null;
	                        gutenberg.blocs.BuildQuiz wrappedParam =
                                                             (gutenberg.blocs.BuildQuiz)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    gutenberg.blocs.BuildQuiz.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               buildQuizResponse47 =
                                                   
                                                   
                                                         skel.buildQuiz(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), buildQuizResponse47, false, new javax.xml.namespace.QName("http://gutenberg/blocs",
                                                    "buildQuiz"));
                                    } else 

            if("generateQuizReport".equals(methodName)){
                
                gutenberg.blocs.GenerateQuizReportResponse generateQuizReportResponse49 = null;
	                        gutenberg.blocs.GenerateQuizReport wrappedParam =
                                                             (gutenberg.blocs.GenerateQuizReport)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    gutenberg.blocs.GenerateQuizReport.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               generateQuizReportResponse49 =
                                                   
                                                   
                                                         skel.generateQuizReport(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), generateQuizReportResponse49, false, new javax.xml.namespace.QName("http://gutenberg/blocs",
                                                    "generateQuizReport"));
                                    } else 

            if("undoAnnotate".equals(methodName)){
                
                gutenberg.blocs.UndoAnnotateResponse undoAnnotateResponse51 = null;
	                        gutenberg.blocs.UndoAnnotate wrappedParam =
                                                             (gutenberg.blocs.UndoAnnotate)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    gutenberg.blocs.UndoAnnotate.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               undoAnnotateResponse51 =
                                                   
                                                   
                                                         skel.undoAnnotate(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), undoAnnotateResponse51, false, new javax.xml.namespace.QName("http://gutenberg/blocs",
                                                    "undoAnnotate"));
                                    } else 

            if("generateStudentRoster".equals(methodName)){
                
                gutenberg.blocs.GenerateStudentRosterResponse generateStudentRosterResponse53 = null;
	                        gutenberg.blocs.GenerateStudentRoster wrappedParam =
                                                             (gutenberg.blocs.GenerateStudentRoster)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    gutenberg.blocs.GenerateStudentRoster.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               generateStudentRosterResponse53 =
                                                   
                                                   
                                                         skel.generateStudentRoster(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), generateStudentRosterResponse53, false, new javax.xml.namespace.QName("http://gutenberg/blocs",
                                                    "generateStudentRoster"));
                                    } else 

            if("generateSuggestionForm".equals(methodName)){
                
                gutenberg.blocs.GenerateSuggestionFormResponse generateSuggestionFormResponse55 = null;
	                        gutenberg.blocs.GenerateSuggestionForm wrappedParam =
                                                             (gutenberg.blocs.GenerateSuggestionForm)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    gutenberg.blocs.GenerateSuggestionForm.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               generateSuggestionFormResponse55 =
                                                   
                                                   
                                                         skel.generateSuggestionForm(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), generateSuggestionFormResponse55, false, new javax.xml.namespace.QName("http://gutenberg/blocs",
                                                    "generateSuggestionForm"));
                                    } else 

            if("rotateScan".equals(methodName)){
                
                gutenberg.blocs.RotateScanResponse rotateScanResponse57 = null;
	                        gutenberg.blocs.RotateScan wrappedParam =
                                                             (gutenberg.blocs.RotateScan)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    gutenberg.blocs.RotateScan.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               rotateScanResponse57 =
                                                   
                                                   
                                                         skel.rotateScan(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), rotateScanResponse57, false, new javax.xml.namespace.QName("http://gutenberg/blocs",
                                                    "rotateScan"));
                                    } else 

            if("assignQuiz".equals(methodName)){
                
                gutenberg.blocs.AssignQuizResponse assignQuizResponse59 = null;
	                        gutenberg.blocs.AssignQuiz wrappedParam =
                                                             (gutenberg.blocs.AssignQuiz)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    gutenberg.blocs.AssignQuiz.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               assignQuizResponse59 =
                                                   
                                                   
                                                         skel.assignQuiz(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), assignQuizResponse59, false, new javax.xml.namespace.QName("http://gutenberg/blocs",
                                                    "assignQuiz"));
                                    
            } else {
              throw new java.lang.RuntimeException("method not found");
            }
        

        newMsgContext.setEnvelope(envelope);
        }
        }
        catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
        }
        
        //
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.TagQuestion param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.TagQuestion.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.TagQuestionResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.TagQuestionResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.UploadSuggestion param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.UploadSuggestion.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.UploadSuggestionResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.UploadSuggestionResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.FetchUnresolvedScans param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.FetchUnresolvedScans.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.FetchUnresolvedScansResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.FetchUnresolvedScansResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.ResolveScan param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.ResolveScan.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.ResolveScanResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.ResolveScanResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.CreateQuestion param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.CreateQuestion.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.CreateQuestionResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.CreateQuestionResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.PrepTest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.PrepTest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.PrepTestResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.PrepTestResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.GenerateStudentCode param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.GenerateStudentCode.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.GenerateStudentCodeResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.GenerateStudentCodeResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.AnnotateScan param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.AnnotateScan.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.AnnotateScanResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.AnnotateScanResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.BuildQuiz param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.BuildQuiz.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.BuildQuizResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.BuildQuizResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.GenerateQuizReport param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.GenerateQuizReport.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.GenerateQuizReportResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.GenerateQuizReportResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.UndoAnnotate param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.UndoAnnotate.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.UndoAnnotateResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.UndoAnnotateResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.GenerateStudentRoster param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.GenerateStudentRoster.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.GenerateStudentRosterResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.GenerateStudentRosterResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.GenerateSuggestionForm param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.GenerateSuggestionForm.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.GenerateSuggestionFormResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.GenerateSuggestionFormResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.RotateScan param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.RotateScan.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.RotateScanResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.RotateScanResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.AssignQuiz param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.AssignQuiz.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.AssignQuizResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.AssignQuizResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, gutenberg.blocs.TagQuestionResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(gutenberg.blocs.TagQuestionResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private gutenberg.blocs.TagQuestionResponse wraptagQuestion(){
                                gutenberg.blocs.TagQuestionResponse wrappedElement = new gutenberg.blocs.TagQuestionResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, gutenberg.blocs.UploadSuggestionResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(gutenberg.blocs.UploadSuggestionResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private gutenberg.blocs.UploadSuggestionResponse wrapuploadSuggestion(){
                                gutenberg.blocs.UploadSuggestionResponse wrappedElement = new gutenberg.blocs.UploadSuggestionResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, gutenberg.blocs.FetchUnresolvedScansResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(gutenberg.blocs.FetchUnresolvedScansResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private gutenberg.blocs.FetchUnresolvedScansResponse wrapfetchUnresolvedScans(){
                                gutenberg.blocs.FetchUnresolvedScansResponse wrappedElement = new gutenberg.blocs.FetchUnresolvedScansResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, gutenberg.blocs.ResolveScanResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(gutenberg.blocs.ResolveScanResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private gutenberg.blocs.ResolveScanResponse wrapresolveScan(){
                                gutenberg.blocs.ResolveScanResponse wrappedElement = new gutenberg.blocs.ResolveScanResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, gutenberg.blocs.CreateQuestionResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(gutenberg.blocs.CreateQuestionResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private gutenberg.blocs.CreateQuestionResponse wrapcreateQuestion(){
                                gutenberg.blocs.CreateQuestionResponse wrappedElement = new gutenberg.blocs.CreateQuestionResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, gutenberg.blocs.PrepTestResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(gutenberg.blocs.PrepTestResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private gutenberg.blocs.PrepTestResponse wrapprepTest(){
                                gutenberg.blocs.PrepTestResponse wrappedElement = new gutenberg.blocs.PrepTestResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, gutenberg.blocs.GenerateStudentCodeResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(gutenberg.blocs.GenerateStudentCodeResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private gutenberg.blocs.GenerateStudentCodeResponse wrapgenerateStudentCode(){
                                gutenberg.blocs.GenerateStudentCodeResponse wrappedElement = new gutenberg.blocs.GenerateStudentCodeResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, gutenberg.blocs.AnnotateScanResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(gutenberg.blocs.AnnotateScanResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private gutenberg.blocs.AnnotateScanResponse wrapannotateScan(){
                                gutenberg.blocs.AnnotateScanResponse wrappedElement = new gutenberg.blocs.AnnotateScanResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, gutenberg.blocs.BuildQuizResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(gutenberg.blocs.BuildQuizResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private gutenberg.blocs.BuildQuizResponse wrapbuildQuiz(){
                                gutenberg.blocs.BuildQuizResponse wrappedElement = new gutenberg.blocs.BuildQuizResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, gutenberg.blocs.GenerateQuizReportResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(gutenberg.blocs.GenerateQuizReportResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private gutenberg.blocs.GenerateQuizReportResponse wrapgenerateQuizReport(){
                                gutenberg.blocs.GenerateQuizReportResponse wrappedElement = new gutenberg.blocs.GenerateQuizReportResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, gutenberg.blocs.UndoAnnotateResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(gutenberg.blocs.UndoAnnotateResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private gutenberg.blocs.UndoAnnotateResponse wrapundoAnnotate(){
                                gutenberg.blocs.UndoAnnotateResponse wrappedElement = new gutenberg.blocs.UndoAnnotateResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, gutenberg.blocs.GenerateStudentRosterResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(gutenberg.blocs.GenerateStudentRosterResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private gutenberg.blocs.GenerateStudentRosterResponse wrapgenerateStudentRoster(){
                                gutenberg.blocs.GenerateStudentRosterResponse wrappedElement = new gutenberg.blocs.GenerateStudentRosterResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, gutenberg.blocs.GenerateSuggestionFormResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(gutenberg.blocs.GenerateSuggestionFormResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private gutenberg.blocs.GenerateSuggestionFormResponse wrapgenerateSuggestionForm(){
                                gutenberg.blocs.GenerateSuggestionFormResponse wrappedElement = new gutenberg.blocs.GenerateSuggestionFormResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, gutenberg.blocs.RotateScanResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(gutenberg.blocs.RotateScanResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private gutenberg.blocs.RotateScanResponse wraprotateScan(){
                                gutenberg.blocs.RotateScanResponse wrappedElement = new gutenberg.blocs.RotateScanResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, gutenberg.blocs.AssignQuizResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(gutenberg.blocs.AssignQuizResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private gutenberg.blocs.AssignQuizResponse wrapassignQuiz(){
                                gutenberg.blocs.AssignQuizResponse wrappedElement = new gutenberg.blocs.AssignQuizResponse();
                                return wrappedElement;
                         }
                    


        /**
        *  get the default envelope
        */
        private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory){
        return factory.getDefaultEnvelope();
        }


        private  java.lang.Object fromOM(
        org.apache.axiom.om.OMElement param,
        java.lang.Class type,
        java.util.Map extraNamespaces) throws org.apache.axis2.AxisFault{

        try {
        
                if (gutenberg.blocs.TagQuestion.class.equals(type)){
                
                           return gutenberg.blocs.TagQuestion.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.TagQuestionResponse.class.equals(type)){
                
                           return gutenberg.blocs.TagQuestionResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.UploadSuggestion.class.equals(type)){
                
                           return gutenberg.blocs.UploadSuggestion.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.UploadSuggestionResponse.class.equals(type)){
                
                           return gutenberg.blocs.UploadSuggestionResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.FetchUnresolvedScans.class.equals(type)){
                
                           return gutenberg.blocs.FetchUnresolvedScans.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.FetchUnresolvedScansResponse.class.equals(type)){
                
                           return gutenberg.blocs.FetchUnresolvedScansResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.ResolveScan.class.equals(type)){
                
                           return gutenberg.blocs.ResolveScan.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.ResolveScanResponse.class.equals(type)){
                
                           return gutenberg.blocs.ResolveScanResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.CreateQuestion.class.equals(type)){
                
                           return gutenberg.blocs.CreateQuestion.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.CreateQuestionResponse.class.equals(type)){
                
                           return gutenberg.blocs.CreateQuestionResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.PrepTest.class.equals(type)){
                
                           return gutenberg.blocs.PrepTest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.PrepTestResponse.class.equals(type)){
                
                           return gutenberg.blocs.PrepTestResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.GenerateStudentCode.class.equals(type)){
                
                           return gutenberg.blocs.GenerateStudentCode.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.GenerateStudentCodeResponse.class.equals(type)){
                
                           return gutenberg.blocs.GenerateStudentCodeResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.AnnotateScan.class.equals(type)){
                
                           return gutenberg.blocs.AnnotateScan.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.AnnotateScanResponse.class.equals(type)){
                
                           return gutenberg.blocs.AnnotateScanResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.BuildQuiz.class.equals(type)){
                
                           return gutenberg.blocs.BuildQuiz.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.BuildQuizResponse.class.equals(type)){
                
                           return gutenberg.blocs.BuildQuizResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.GenerateQuizReport.class.equals(type)){
                
                           return gutenberg.blocs.GenerateQuizReport.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.GenerateQuizReportResponse.class.equals(type)){
                
                           return gutenberg.blocs.GenerateQuizReportResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.UndoAnnotate.class.equals(type)){
                
                           return gutenberg.blocs.UndoAnnotate.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.UndoAnnotateResponse.class.equals(type)){
                
                           return gutenberg.blocs.UndoAnnotateResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.GenerateStudentRoster.class.equals(type)){
                
                           return gutenberg.blocs.GenerateStudentRoster.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.GenerateStudentRosterResponse.class.equals(type)){
                
                           return gutenberg.blocs.GenerateStudentRosterResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.GenerateSuggestionForm.class.equals(type)){
                
                           return gutenberg.blocs.GenerateSuggestionForm.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.GenerateSuggestionFormResponse.class.equals(type)){
                
                           return gutenberg.blocs.GenerateSuggestionFormResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.RotateScan.class.equals(type)){
                
                           return gutenberg.blocs.RotateScan.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.RotateScanResponse.class.equals(type)){
                
                           return gutenberg.blocs.RotateScanResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.AssignQuiz.class.equals(type)){
                
                           return gutenberg.blocs.AssignQuiz.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.AssignQuizResponse.class.equals(type)){
                
                           return gutenberg.blocs.AssignQuizResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
        } catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
           return null;
        }



    

        /**
        *  A utility method that copies the namepaces from the SOAPEnvelope
        */
        private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env){
        java.util.Map returnMap = new java.util.HashMap();
        java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
        while (namespaceIterator.hasNext()) {
        org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
        returnMap.put(ns.getPrefix(),ns.getNamespaceURI());
        }
        return returnMap;
        }

        private org.apache.axis2.AxisFault createAxisFault(java.lang.Exception e) {
        org.apache.axis2.AxisFault f;
        Throwable cause = e.getCause();
        if (cause != null) {
            f = new org.apache.axis2.AxisFault(e.getMessage(), cause);
        } else {
            f = new org.apache.axis2.AxisFault(e.getMessage());
        }

        return f;
    }

        }//end of class
    