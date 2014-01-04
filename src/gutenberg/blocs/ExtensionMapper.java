
/**
 * ExtensionMapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

        
            package gutenberg.blocs;
        
            /**
            *  ExtensionMapper class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class ExtensionMapper{

          public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
                                                       java.lang.String typeName,
                                                       javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{

              
                  if (
                  "http://gutenberg/blocs".equals(namespaceURI) &&
                  "lengthType".equals(typeName)){
                   
                            return  gutenberg.blocs.LengthType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://gutenberg/blocs".equals(namespaceURI) &&
                  "scanIdType".equals(typeName)){
                   
                            return  gutenberg.blocs.ScanIdType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://gutenberg/blocs".equals(namespaceURI) &&
                  "qFlagsType".equals(typeName)){
                   
                            return  gutenberg.blocs.QFlagsType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://gutenberg/blocs".equals(namespaceURI) &&
                  "MkFlags".equals(typeName)){
                   
                            return  gutenberg.blocs.MkFlags.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://gutenberg/blocs".equals(namespaceURI) &&
                  "questionTagsType".equals(typeName)){
                   
                            return  gutenberg.blocs.QuestionTagsType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://gutenberg/blocs".equals(namespaceURI) &&
                  "wFlagsType".equals(typeName)){
                   
                            return  gutenberg.blocs.WFlagsType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://gutenberg/blocs".equals(namespaceURI) &&
                  "studentGroupType".equals(typeName)){
                   
                            return  gutenberg.blocs.StudentGroupType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://gutenberg/blocs".equals(namespaceURI) &&
                  "TexFlags".equals(typeName)){
                   
                            return  gutenberg.blocs.TexFlags.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://gutenberg/blocs".equals(namespaceURI) &&
                  "manifestType".equals(typeName)){
                   
                            return  gutenberg.blocs.ManifestType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://gutenberg/blocs".equals(namespaceURI) &&
                  "pointType".equals(typeName)){
                   
                            return  gutenberg.blocs.PointType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://gutenberg/blocs".equals(namespaceURI) &&
                  "workRequestType".equals(typeName)){
                   
                            return  gutenberg.blocs.WorkRequestType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://gutenberg/blocs".equals(namespaceURI) &&
                  "quizType".equals(typeName)){
                   
                            return  gutenberg.blocs.QuizType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://gutenberg/blocs".equals(namespaceURI) &&
                  "assignmentType".equals(typeName)){
                   
                            return  gutenberg.blocs.AssignmentType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://gutenberg/blocs".equals(namespaceURI) &&
                  "entryType".equals(typeName)){
                   
                            return  gutenberg.blocs.EntryType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://gutenberg/blocs".equals(namespaceURI) &&
                  "responseType".equals(typeName)){
                   
                            return  gutenberg.blocs.ResponseType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://gutenberg/blocs".equals(namespaceURI) &&
                  "suggestionType".equals(typeName)){
                   
                            return  gutenberg.blocs.SuggestionType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://gutenberg/blocs".equals(namespaceURI) &&
                  "annotationType".equals(typeName)){
                   
                            return  gutenberg.blocs.AnnotationType.Factory.parse(reader);
                        

                  }

              
             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
          }

        }
    