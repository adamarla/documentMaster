
/**
 * QuizType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

            
                package gutenberg.blocs;
            

            /**
            *  QuizType bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class QuizType
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = quizType
                Namespace URI = http://gutenberg/blocs
                Namespace Prefix = ns1
                */
            

                        /**
                        * field for Quiz
                        */

                        
                                    protected gutenberg.blocs.EntryType localQuiz ;
                                

                           /**
                           * Auto generated getter method
                           * @return gutenberg.blocs.EntryType
                           */
                           public  gutenberg.blocs.EntryType getQuiz(){
                               return localQuiz;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Quiz
                               */
                               public void setQuiz(gutenberg.blocs.EntryType param){
                            
                                            this.localQuiz=param;
                                    

                               }
                            

                        /**
                        * field for Teacher
                        */

                        
                                    protected gutenberg.blocs.EntryType localTeacher ;
                                

                           /**
                           * Auto generated getter method
                           * @return gutenberg.blocs.EntryType
                           */
                           public  gutenberg.blocs.EntryType getTeacher(){
                               return localTeacher;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Teacher
                               */
                               public void setTeacher(gutenberg.blocs.EntryType param){
                            
                                            this.localTeacher=param;
                                    

                               }
                            

                        /**
                        * field for Questions
                        * This was an Array!
                        */

                        
                                    protected gutenberg.blocs.EntryType[] localQuestions ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localQuestionsTracker = false ;

                           public boolean isQuestionsSpecified(){
                               return localQuestionsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return gutenberg.blocs.EntryType[]
                           */
                           public  gutenberg.blocs.EntryType[] getQuestions(){
                               return localQuestions;
                           }

                           
                        


                               
                              /**
                               * validate the array for Questions
                               */
                              protected void validateQuestions(gutenberg.blocs.EntryType[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param Questions
                              */
                              public void setQuestions(gutenberg.blocs.EntryType[] param){
                              
                                   validateQuestions(param);

                               localQuestionsTracker = param != null;
                                      
                                      this.localQuestions=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param gutenberg.blocs.EntryType
                             */
                             public void addQuestions(gutenberg.blocs.EntryType param){
                                   if (localQuestions == null){
                                   localQuestions = new gutenberg.blocs.EntryType[]{};
                                   }

                            
                                 //update the setting tracker
                                localQuestionsTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localQuestions);
                               list.add(param);
                               this.localQuestions =
                             (gutenberg.blocs.EntryType[])list.toArray(
                            new gutenberg.blocs.EntryType[list.size()]);

                             }
                             

                        /**
                        * field for Breaks
                        * This was an Array!
                        */

                        
                                    protected int[] localBreaks ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localBreaksTracker = false ;

                           public boolean isBreaksSpecified(){
                               return localBreaksTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return int[]
                           */
                           public  int[] getBreaks(){
                               return localBreaks;
                           }

                           
                        


                               
                              /**
                               * validate the array for Breaks
                               */
                              protected void validateBreaks(int[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param Breaks
                              */
                              public void setBreaks(int[] param){
                              
                                   validateBreaks(param);

                               localBreaksTracker = param != null;
                                      
                                      this.localBreaks=param;
                              }

                               
                             

                        /**
                        * field for VersionTriggers
                        * This was an Array!
                        */

                        
                                    protected int[] localVersionTriggers ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localVersionTriggersTracker = false ;

                           public boolean isVersionTriggersSpecified(){
                               return localVersionTriggersTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return int[]
                           */
                           public  int[] getVersionTriggers(){
                               return localVersionTriggers;
                           }

                           
                        


                               
                              /**
                               * validate the array for VersionTriggers
                               */
                              protected void validateVersionTriggers(int[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param VersionTriggers
                              */
                              public void setVersionTriggers(int[] param){
                              
                                   validateVersionTriggers(param);

                               localVersionTriggersTracker = param != null;
                                      
                                      this.localVersionTriggers=param;
                              }

                               
                             

     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName);
               return factory.createOMElement(dataSource,parentQName);
            
        }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       javax.xml.stream.XMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               javax.xml.stream.XMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://gutenberg/blocs");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":quizType",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "quizType",
                           xmlWriter);
                   }

               
                   }
               
                                            if (localQuiz==null){
                                                 throw new org.apache.axis2.databinding.ADBException("quiz cannot be null!!");
                                            }
                                           localQuiz.serialize(new javax.xml.namespace.QName("http://gutenberg/blocs","quiz"),
                                               xmlWriter);
                                        
                                            if (localTeacher==null){
                                                 throw new org.apache.axis2.databinding.ADBException("teacher cannot be null!!");
                                            }
                                           localTeacher.serialize(new javax.xml.namespace.QName("http://gutenberg/blocs","teacher"),
                                               xmlWriter);
                                         if (localQuestionsTracker){
                                       if (localQuestions!=null){
                                            for (int i = 0;i < localQuestions.length;i++){
                                                if (localQuestions[i] != null){
                                                 localQuestions[i].serialize(new javax.xml.namespace.QName("http://gutenberg/blocs","questions"),
                                                           xmlWriter);
                                                } else {
                                                   
                                                        // we don't have to do any thing since minOccures is zero
                                                    
                                                }

                                            }
                                     } else {
                                        
                                               throw new org.apache.axis2.databinding.ADBException("questions cannot be null!!");
                                        
                                    }
                                 } if (localBreaksTracker){
                             if (localBreaks!=null) {
                                   namespace = "http://gutenberg/blocs";
                                   for (int i = 0;i < localBreaks.length;i++){
                                        
                                                   if (localBreaks[i]!=java.lang.Integer.MIN_VALUE) {
                                               
                                                writeStartElement(null, namespace, "breaks", xmlWriter);

                                            
                                                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBreaks[i]));
                                                xmlWriter.writeEndElement();
                                            
                                                } else {
                                                   
                                                           // we have to do nothing since minOccurs is zero
                                                       
                                                }

                                   }
                             } else {
                                 
                                         throw new org.apache.axis2.databinding.ADBException("breaks cannot be null!!");
                                    
                             }

                        } if (localVersionTriggersTracker){
                             if (localVersionTriggers!=null) {
                                   namespace = "http://gutenberg/blocs";
                                   for (int i = 0;i < localVersionTriggers.length;i++){
                                        
                                                   if (localVersionTriggers[i]!=java.lang.Integer.MIN_VALUE) {
                                               
                                                writeStartElement(null, namespace, "versionTriggers", xmlWriter);

                                            
                                                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localVersionTriggers[i]));
                                                xmlWriter.writeEndElement();
                                            
                                                } else {
                                                   
                                                           // we have to do nothing since minOccurs is zero
                                                       
                                                }

                                   }
                             } else {
                                 
                                         throw new org.apache.axis2.databinding.ADBException("versionTriggers cannot be null!!");
                                    
                             }

                        }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://gutenberg/blocs")){
                return "ns1";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace, localPart);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }
        
        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            xmlWriter.writeAttribute(namespace,attName,attValue);
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName,attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace,attName,attValue);
            }
        }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null) {
                prefix = generatePrefix(namespace);
                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);
                    if (uri == null || uri.length() == 0) {
                        break;
                    }
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            return prefix;
        }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                
                            elementList.add(new javax.xml.namespace.QName("http://gutenberg/blocs",
                                                                      "quiz"));
                            
                            
                                    if (localQuiz==null){
                                         throw new org.apache.axis2.databinding.ADBException("quiz cannot be null!!");
                                    }
                                    elementList.add(localQuiz);
                                
                            elementList.add(new javax.xml.namespace.QName("http://gutenberg/blocs",
                                                                      "teacher"));
                            
                            
                                    if (localTeacher==null){
                                         throw new org.apache.axis2.databinding.ADBException("teacher cannot be null!!");
                                    }
                                    elementList.add(localTeacher);
                                 if (localQuestionsTracker){
                             if (localQuestions!=null) {
                                 for (int i = 0;i < localQuestions.length;i++){

                                    if (localQuestions[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("http://gutenberg/blocs",
                                                                          "questions"));
                                         elementList.add(localQuestions[i]);
                                    } else {
                                        
                                                // nothing to do
                                            
                                    }

                                 }
                             } else {
                                 
                                        throw new org.apache.axis2.databinding.ADBException("questions cannot be null!!");
                                    
                             }

                        } if (localBreaksTracker){
                            if (localBreaks!=null){
                                  for (int i = 0;i < localBreaks.length;i++){
                                      
                                          elementList.add(new javax.xml.namespace.QName("http://gutenberg/blocs",
                                                                                                                       "breaks"));
                                          elementList.add(
                                          org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBreaks[i]));

                                      

                                  }
                            } else {
                              
                                    throw new org.apache.axis2.databinding.ADBException("breaks cannot be null!!");
                                
                            }

                        } if (localVersionTriggersTracker){
                            if (localVersionTriggers!=null){
                                  for (int i = 0;i < localVersionTriggers.length;i++){
                                      
                                          elementList.add(new javax.xml.namespace.QName("http://gutenberg/blocs",
                                                                                                                       "versionTriggers"));
                                          elementList.add(
                                          org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localVersionTriggers[i]));

                                      

                                  }
                            } else {
                              
                                    throw new org.apache.axis2.databinding.ADBException("versionTriggers cannot be null!!");
                                
                            }

                        }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static QuizType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            QuizType object =
                new QuizType();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"quizType".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (QuizType)gutenberg.blocs.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                        java.util.ArrayList list3 = new java.util.ArrayList();
                    
                        java.util.ArrayList list4 = new java.util.ArrayList();
                    
                        java.util.ArrayList list5 = new java.util.ArrayList();
                    
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://gutenberg/blocs","quiz").equals(reader.getName())){
                                
                                                object.setQuiz(gutenberg.blocs.EntryType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://gutenberg/blocs","teacher").equals(reader.getName())){
                                
                                                object.setTeacher(gutenberg.blocs.EntryType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://gutenberg/blocs","questions").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list3.add(gutenberg.blocs.EntryType.Factory.parse(reader));
                                                                
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone3 = false;
                                                        while(!loopDone3){
                                                            // We should be at the end element, but make sure
                                                            while (!reader.isEndElement())
                                                                reader.next();
                                                            // Step out of this element
                                                            reader.next();
                                                            // Step to next element event.
                                                            while (!reader.isStartElement() && !reader.isEndElement())
                                                                reader.next();
                                                            if (reader.isEndElement()){
                                                                //two continuous end elements means we are exiting the xml structure
                                                                loopDone3 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://gutenberg/blocs","questions").equals(reader.getName())){
                                                                    list3.add(gutenberg.blocs.EntryType.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone3 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setQuestions((gutenberg.blocs.EntryType[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                gutenberg.blocs.EntryType.class,
                                                                list3));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://gutenberg/blocs","breaks").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list4.add(reader.getElementText());
                                            
                                            //loop until we find a start element that is not part of this array
                                            boolean loopDone4 = false;
                                            while(!loopDone4){
                                                // Ensure we are at the EndElement
                                                while (!reader.isEndElement()){
                                                    reader.next();
                                                }
                                                // Step out of this element
                                                reader.next();
                                                // Step to next element event.
                                                while (!reader.isStartElement() && !reader.isEndElement())
                                                    reader.next();
                                                if (reader.isEndElement()){
                                                    //two continuous end elements means we are exiting the xml structure
                                                    loopDone4 = true;
                                                } else {
                                                    if (new javax.xml.namespace.QName("http://gutenberg/blocs","breaks").equals(reader.getName())){
                                                         list4.add(reader.getElementText());
                                                        
                                                    }else{
                                                        loopDone4 = true;
                                                    }
                                                }
                                            }
                                            // call the converter utility  to convert and set the array
                                            
                                            object.setBreaks((int[])
                                                org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                            int.class,list4));
                                                
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://gutenberg/blocs","versionTriggers").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list5.add(reader.getElementText());
                                            
                                            //loop until we find a start element that is not part of this array
                                            boolean loopDone5 = false;
                                            while(!loopDone5){
                                                // Ensure we are at the EndElement
                                                while (!reader.isEndElement()){
                                                    reader.next();
                                                }
                                                // Step out of this element
                                                reader.next();
                                                // Step to next element event.
                                                while (!reader.isStartElement() && !reader.isEndElement())
                                                    reader.next();
                                                if (reader.isEndElement()){
                                                    //two continuous end elements means we are exiting the xml structure
                                                    loopDone5 = true;
                                                } else {
                                                    if (new javax.xml.namespace.QName("http://gutenberg/blocs","versionTriggers").equals(reader.getName())){
                                                         list5.add(reader.getElementText());
                                                        
                                                    }else{
                                                        loopDone5 = true;
                                                    }
                                                }
                                            }
                                            // call the converter utility  to convert and set the array
                                            
                                            object.setVersionTriggers((int[])
                                                org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                            int.class,list5));
                                                
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                  
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    