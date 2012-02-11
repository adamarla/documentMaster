
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


        

            if("viewScans".equals(methodName)){
                
                gutenberg.blocs.ViewScansResponse viewScansResponse13 = null;
	                        gutenberg.blocs.ViewScans wrappedParam =
                                                             (gutenberg.blocs.ViewScans)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    gutenberg.blocs.ViewScans.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               viewScansResponse13 =
                                                   
                                                   
                                                         skel.viewScans(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), viewScansResponse13, false, new javax.xml.namespace.QName("http://gutenberg/blocs",
                                                    "viewScans"));
                                    } else 

            if("viewQuestions".equals(methodName)){
                
                gutenberg.blocs.ViewQuestionsResponse viewQuestionsResponse15 = null;
	                        gutenberg.blocs.ViewQuestions wrappedParam =
                                                             (gutenberg.blocs.ViewQuestions)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    gutenberg.blocs.ViewQuestions.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               viewQuestionsResponse15 =
                                                   
                                                   
                                                         skel.viewQuestions(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), viewQuestionsResponse15, false, new javax.xml.namespace.QName("http://gutenberg/blocs",
                                                    "viewQuestions"));
                                    } else 

            if("createQuestion".equals(methodName)){
                
                gutenberg.blocs.CreateQuestionResponse createQuestionResponse17 = null;
	                        gutenberg.blocs.CreateQuestion wrappedParam =
                                                             (gutenberg.blocs.CreateQuestion)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    gutenberg.blocs.CreateQuestion.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createQuestionResponse17 =
                                                   
                                                   
                                                         skel.createQuestion(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createQuestionResponse17, false, new javax.xml.namespace.QName("http://gutenberg/blocs",
                                                    "createQuestion"));
                                    } else 

            if("buildQuiz".equals(methodName)){
                
                gutenberg.blocs.BuildQuizResponse buildQuizResponse19 = null;
	                        gutenberg.blocs.BuildQuiz wrappedParam =
                                                             (gutenberg.blocs.BuildQuiz)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    gutenberg.blocs.BuildQuiz.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               buildQuizResponse19 =
                                                   
                                                   
                                                         skel.buildQuiz(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), buildQuizResponse19, false, new javax.xml.namespace.QName("http://gutenberg/blocs",
                                                    "buildQuiz"));
                                    } else 

            if("receiveScans".equals(methodName)){
                
                gutenberg.blocs.ReceiveScansResponse receiveScansResponse21 = null;
	                        gutenberg.blocs.ReceiveScans wrappedParam =
                                                             (gutenberg.blocs.ReceiveScans)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    gutenberg.blocs.ReceiveScans.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               receiveScansResponse21 =
                                                   
                                                   
                                                         skel.receiveScans(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), receiveScansResponse21, false, new javax.xml.namespace.QName("http://gutenberg/blocs",
                                                    "receiveScans"));
                                    } else 

            if("assignQuiz".equals(methodName)){
                
                gutenberg.blocs.AssignQuizResponse assignQuizResponse23 = null;
	                        gutenberg.blocs.AssignQuiz wrappedParam =
                                                             (gutenberg.blocs.AssignQuiz)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    gutenberg.blocs.AssignQuiz.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               assignQuizResponse23 =
                                                   
                                                   
                                                         skel.assignQuiz(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), assignQuizResponse23, false, new javax.xml.namespace.QName("http://gutenberg/blocs",
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
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.ViewScans param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.ViewScans.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.ViewScansResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.ViewScansResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.ViewQuestions param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.ViewQuestions.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.ViewQuestionsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.ViewQuestionsResponse.MY_QNAME,
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
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.ReceiveScans param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.ReceiveScans.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(gutenberg.blocs.ReceiveScansResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(gutenberg.blocs.ReceiveScansResponse.MY_QNAME,
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
        
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, gutenberg.blocs.ViewScansResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(gutenberg.blocs.ViewScansResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private gutenberg.blocs.ViewScansResponse wrapviewScans(){
                                gutenberg.blocs.ViewScansResponse wrappedElement = new gutenberg.blocs.ViewScansResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, gutenberg.blocs.ViewQuestionsResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(gutenberg.blocs.ViewQuestionsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private gutenberg.blocs.ViewQuestionsResponse wrapviewQuestions(){
                                gutenberg.blocs.ViewQuestionsResponse wrappedElement = new gutenberg.blocs.ViewQuestionsResponse();
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
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, gutenberg.blocs.ReceiveScansResponse param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(gutenberg.blocs.ReceiveScansResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private gutenberg.blocs.ReceiveScansResponse wrapreceiveScans(){
                                gutenberg.blocs.ReceiveScansResponse wrappedElement = new gutenberg.blocs.ReceiveScansResponse();
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
        
                if (gutenberg.blocs.ViewScans.class.equals(type)){
                
                           return gutenberg.blocs.ViewScans.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.ViewScansResponse.class.equals(type)){
                
                           return gutenberg.blocs.ViewScansResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.ViewQuestions.class.equals(type)){
                
                           return gutenberg.blocs.ViewQuestions.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.ViewQuestionsResponse.class.equals(type)){
                
                           return gutenberg.blocs.ViewQuestionsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.CreateQuestion.class.equals(type)){
                
                           return gutenberg.blocs.CreateQuestion.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.CreateQuestionResponse.class.equals(type)){
                
                           return gutenberg.blocs.CreateQuestionResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.BuildQuiz.class.equals(type)){
                
                           return gutenberg.blocs.BuildQuiz.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.BuildQuizResponse.class.equals(type)){
                
                           return gutenberg.blocs.BuildQuizResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.ReceiveScans.class.equals(type)){
                
                           return gutenberg.blocs.ReceiveScans.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (gutenberg.blocs.ReceiveScansResponse.class.equals(type)){
                
                           return gutenberg.blocs.ReceiveScansResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

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
    