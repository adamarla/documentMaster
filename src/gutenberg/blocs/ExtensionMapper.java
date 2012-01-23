
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
                  "studentType".equals(typeName)){
                   
                            return  gutenberg.blocs.StudentType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://gutenberg/blocs".equals(namespaceURI) &&
                  "manifestType".equals(typeName)){
                   
                            return  gutenberg.blocs.ManifestType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://gutenberg/blocs".equals(namespaceURI) &&
                  "pagesType".equals(typeName)){
                   
                            return  gutenberg.blocs.PagesType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://gutenberg/blocs".equals(namespaceURI) &&
                  "studentsType".equals(typeName)){
                   
                            return  gutenberg.blocs.StudentsType.Factory.parse(reader);
                        

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
                  "idListType".equals(typeName)){
                   
                            return  gutenberg.blocs.IdListType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://gutenberg/blocs".equals(namespaceURI) &&
                  "responseType".equals(typeName)){
                   
                            return  gutenberg.blocs.ResponseType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://gutenberg/blocs".equals(namespaceURI) &&
                  "pageType".equals(typeName)){
                   
                            return  gutenberg.blocs.PageType.Factory.parse(reader);
                        

                  }

              
             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
          }

        }
    