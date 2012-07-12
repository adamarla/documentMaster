
/**
 * ExtensionMapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1  Built on : Aug 31, 2011 (12:23:23 CEST)
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
                  "entryListType".equals(typeName)){
                   
                            return  gutenberg.blocs.EntryListType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://gutenberg/blocs".equals(namespaceURI) &&
                  "questionTagsType".equals(typeName)){
                   
                            return  gutenberg.blocs.QuestionTagsType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://gutenberg/blocs".equals(namespaceURI) &&
                  "teacherType".equals(typeName)){
                   
                            return  gutenberg.blocs.TeacherType.Factory.parse(reader);
                        

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
                  "quizType".equals(typeName)){
                   
                            return  gutenberg.blocs.QuizType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://gutenberg/blocs".equals(namespaceURI) &&
                  "questionType".equals(typeName)){
                   
                            return  gutenberg.blocs.QuestionType.Factory.parse(reader);
                        

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
                  "annotationType".equals(typeName)){
                   
                            return  gutenberg.blocs.AnnotationType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://gutenberg/blocs".equals(namespaceURI) &&
                  "pageType".equals(typeName)){
                   
                            return  gutenberg.blocs.PageType.Factory.parse(reader);
                        

                  }

              
             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
          }

        }
    