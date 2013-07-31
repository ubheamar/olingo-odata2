/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 *        or more contributor license agreements.  See the NOTICE file
 *        distributed with this work for additional information
 *        regarding copyright ownership.  The ASF licenses this file
 *        to you under the Apache License, Version 2.0 (the
 *        "License"); you may not use this file except in compliance
 *        with the License.  You may obtain a copy of the License at
 * 
 *          http://www.apache.org/licenses/LICENSE-2.0
 * 
 *        Unless required by applicable law or agreed to in writing,
 *        software distributed under the License is distributed on an
 *        "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *        KIND, either express or implied.  See the License for the
 *        specific language governing permissions and limitations
 *        under the License.
 ******************************************************************************/
package org.apache.olingo.odata2.processor.core.jpa.cud;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmStructuralType;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.uri.info.PostUriInfo;
import org.apache.olingo.odata2.core.ep.entry.EntryMetadataImpl;
import org.apache.olingo.odata2.core.ep.entry.MediaMetadataImpl;
import org.apache.olingo.odata2.core.ep.entry.ODataEntryImpl;
import org.apache.olingo.odata2.core.ep.feed.FeedMetadataImpl;
import org.apache.olingo.odata2.core.ep.feed.ODataFeedImpl;
import org.apache.olingo.odata2.core.uri.ExpandSelectTreeNodeImpl;
import org.apache.olingo.odata2.processor.api.jpa.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.processor.core.jpa.common.ODataJPATestConstants;
import org.junit.Test;

public class JPACreateRequestTest {

  @Test
  public void testProcess() {

    JPACreateRequest createRequest = new JPACreateRequest(JPATestUtil.mockMetaModel());
    PostUriInfo postUriInfo = JPATestUtil.getPostUriInfo();
    try {
      createRequest.process(postUriInfo, null, "application/xml");
    } catch (ODataJPARuntimeException e) {
      if (e.isCausedByMessageException()) {
        assertTrue(true);
      } else {
        fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
      }
    }

  }

  @Test
  public void testGetSetterName() {
    JPACreateRequest createRequest = new JPACreateRequest();
    Method method = getMethodForTesting("getSetterName", createRequest);
    if (method != null) {
      method.setAccessible(true);
      Object[] actualParams = { "salesOrderItems" };
      try {
        String result = (String) method.invoke(createRequest, actualParams);
        assertEquals("setSalesOrderItems", result);
      } catch (IllegalArgumentException e) {
        fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
      } catch (IllegalAccessException e) {
        fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
      } catch (InvocationTargetException e) {
        fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
      }
    }

  }

  @Test
  public void testParse2JPAEntityValueMap() {
    JPACreateRequest createRequest = new JPACreateRequest();
    EdmStructuralType edmEntityType = JPATestUtil.getEdmStructuralType();
    Object result = null;
    try {
      result = createRequest.parse2JPAEntityValueMap(JPATestUtil.getJPAEntity(), edmEntityType, JPATestUtil.getPropertyValueMap(), "SalesOrderHeader");
    } catch (ODataJPARuntimeException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
    assertNotNull(result);
    assertEquals(((SalesOrderHeader) result).getId(), 1);

  }

  @Test
  public void testCreateInlinedEntities() {
    JPACreateRequest createRequest = new JPACreateRequest(JPATestUtil.mockMetaModel());
    Method method = getMethodForTesting("createInlinedEntities", createRequest);
    if (method != null) {
      method.setAccessible(true);
      EdmEntitySet edmEntitySet = JPATestUtil.mockSourceEdmEntitySet();
      ODataEntryImpl odataEntry = createODataEntry();
      Object[] actualParams = { JPATestUtil.getJPAEntity(), edmEntitySet, odataEntry, "SalesOrderHeader" };
      try {

        method.invoke(createRequest, actualParams);

      } catch (IllegalArgumentException e) {
        fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
      } catch (IllegalAccessException e) {
        fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
      } catch (InvocationTargetException e) {
        fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
      }

    }

  }

  @Test
  public void testGetSettersForNavigationProperties() {
    JPACreateRequest createRequest = new JPACreateRequest();
    Method method = getMethodForTesting("getSettersForNavigationProperties", createRequest);
    if (method != null) {
      method.setAccessible(true);
      Map<String, Class<?>> relatedClassMap = new HashMap<String, Class<?>>();
      relatedClassMap.put("salesOrderLineItems", SalesOrderLineItem.class);
      Object[] actualParams = { JPATestUtil.getJPAEntity(), JPATestUtil.mockSourceEdmEntitySet(), relatedClassMap };
      try {
        @SuppressWarnings("unchecked")
        List<HashMap<?, ?>> result = (List<HashMap<?, ?>>) method.invoke(createRequest, actualParams);
        assertEquals(2, result.size());
      } catch (IllegalArgumentException e) {
        fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
      } catch (IllegalAccessException e) {
        fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
      } catch (InvocationTargetException e) {
        fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
      }

    }
  }

  @Test
  public void testSetNavigationProperties() {
    JPACreateRequest createRequest = new JPACreateRequest();
    Method method = getMethodForTesting("setNavigationProperties", createRequest);

    if (method != null) {
      method.setAccessible(true);
      Map<String, Class<?>> relatedClassMap = new HashMap<String, Class<?>>();
      relatedClassMap.put("salesOrderLineItems", SalesOrderLineItem.class);
      Map<String, Object> propertyValueMap = new HashMap<String, Object>();
      propertyValueMap.put("id", 1);
      List<Object> objectList = new ArrayList<Object>();
      objectList.add(new SalesOrderLineItem(23));
      propertyValueMap.put("salesOrderLineItems", objectList);
      Object[] actualParams = { JPATestUtil.getJPAEntity(), JPATestUtil.mockSourceEdmEntitySet(), propertyValueMap, "SalesOrderHeader", relatedClassMap };
      try {
        method.invoke(createRequest, actualParams);
        //This method returns nothing. It only sets value in an object.
        // If no exception is thrown then we assert true
        assertTrue(true);
      } catch (IllegalArgumentException e) {
        fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
      } catch (IllegalAccessException e) {
        fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
      } catch (InvocationTargetException e) {
        fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
      }
    }
  }

  @Test
  public void testPopulateEmbeddableKey() {
    JPACreateRequest createRequest = new JPACreateRequest();
    Method method = getMethodForTesting("populateEmbeddableKey", createRequest);
    if (method != null) {
      method.setAccessible(true);
      SalesOrderLineItemKey embeddableKeyObject = new SalesOrderLineItemKey();
      String key = "soId";
      String setterName = "setSoId";
      Map<String, Object> propertyValueMap = new HashMap<String, Object>();
      propertyValueMap.put("soId", 23);
      propertyValueMap.put("liId", 45);
      Field field = null;
      Map<String, Class<?>> jpaEmbeddableObjectKeyMap = new HashMap<String, Class<?>>();
      jpaEmbeddableObjectKeyMap.put("soId", int.class);
      jpaEmbeddableObjectKeyMap.put("liId", int.class);
      Object[] actualParams = { embeddableKeyObject, key, setterName, propertyValueMap };
      try {
        for (Field f : createRequest.getClass().getSuperclass().getDeclaredFields()) {
          if (f.getName().equalsIgnoreCase("jpaEmbeddableKeyObjectMap")) {
            field = f;
            break;
          }
        }
        field.setAccessible(true);
        field.set(createRequest, jpaEmbeddableObjectKeyMap);
        method.invoke(createRequest, actualParams);
      } catch (IllegalArgumentException e) {
        fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
      } catch (IllegalAccessException e) {
        fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
      } catch (InvocationTargetException e) {
        fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
      } catch (SecurityException e) {
        fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
      }
    }
  }

  @Test
  public void testPopulateEmbeddableKeyInvocationIssue() {
    JPACreateRequest createRequest = new JPACreateRequest();
    Method method = getMethodForTesting("populateEmbeddableKey", createRequest);
    if (method != null) {
      method.setAccessible(true);
      SalesOrderLineItemKey embeddableKeyObject = new SalesOrderLineItemKey();
      String key = "soId";
      String setterName = "setSoId";
      Map<String, Object> propertyValueMap = new HashMap<String, Object>();
      propertyValueMap.put("soId", "23");
      propertyValueMap.put("liId", "45");
      Field field = null;
      Map<String, Class<?>> jpaEmbeddableObjectKeyMap = new HashMap<String, Class<?>>();
      jpaEmbeddableObjectKeyMap.put("soId", int.class);
      jpaEmbeddableObjectKeyMap.put("liId", int.class);
      Object[] actualParams = { embeddableKeyObject, key, setterName, propertyValueMap };
      //			Object[] incorrectParams = {embeddableKeyObject,key,setterName,propertyValueMap};
      try {
        for (Field f : createRequest.getClass().getSuperclass().getDeclaredFields()) {
          if (f.getName().equalsIgnoreCase("jpaEmbeddableKeyObjectMap")) {
            field = f;
            break;
          }
        }
        field.setAccessible(true);
        field.set(createRequest, jpaEmbeddableObjectKeyMap);
        method.invoke(createRequest, actualParams);
      } catch (IllegalArgumentException e) {
        fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
      } catch (IllegalAccessException e) {
        fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
      } catch (InvocationTargetException e) {
        assertTrue(true);
      } catch (SecurityException e) {
        fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
      }
    }
  }

  @Test
  public void testPopulateEmbeddableKeyNoSuchMethod() {
    JPACreateRequest createRequest = new JPACreateRequest();
    Method method = getMethodForTesting("populateEmbeddableKey", createRequest);
    if (method != null) {
      method.setAccessible(true);
      SalesOrderLineItemKey embeddableKeyObject = new SalesOrderLineItemKey();
      String key = "soId";
      String setterName = "setPoId";
      Map<String, Object> propertyValueMap = new HashMap<String, Object>();
      propertyValueMap.put("soId", 23);
      propertyValueMap.put("liId", 45);
      Field field = null;
      Map<String, Class<?>> jpaEmbeddableObjectKeyMap = new HashMap<String, Class<?>>();
      jpaEmbeddableObjectKeyMap.put("soId", int.class);
      jpaEmbeddableObjectKeyMap.put("liId", int.class);
      Object[] actualParams = { embeddableKeyObject, key, setterName, propertyValueMap };
      try {
        for (Field f : createRequest.getClass().getSuperclass().getDeclaredFields()) {
          if (f.getName().equalsIgnoreCase("jpaEmbeddableKeyObjectMap")) {
            field = f;
            break;
          }
        }
        field.setAccessible(true);
        field.set(createRequest, jpaEmbeddableObjectKeyMap);
        method.invoke(createRequest, actualParams);
      } catch (IllegalArgumentException e) {
        fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
      } catch (IllegalAccessException e) {
        fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
      } catch (InvocationTargetException e) {
        assertTrue(true);
      } catch (SecurityException e) {
        fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
      }
    }
  }

  private ODataEntryImpl createODataEntry() {
    HashMap<String, Object> data = new HashMap<String, Object>();
    HashMap<String, Object> data1 = new HashMap<String, Object>();
    List<ODataEntry> oDataEntryList = new ArrayList<ODataEntry>();

    MediaMetadataImpl mediaMetadata = new MediaMetadataImpl();
    mediaMetadata.setContentType("application/xml");
    EntryMetadataImpl entryMetadata = new EntryMetadataImpl();
    entryMetadata.setId("http://localhost:8080/org.apache.olingo.odata2.processor.ref.web/SalesOrderProcessing.svc/SalesOrders(5L)");
    ExpandSelectTreeNodeImpl expandSelectTree = new ExpandSelectTreeNodeImpl();
    expandSelectTree.putLink("SalesOrderLineItemDetails", new ExpandSelectTreeNodeImpl());
    expandSelectTree.setExpanded();
    expandSelectTree.setAllExplicitly();
    data1.put("id", 1);
    data1.put("SalesOrderLineItemDetails", new SalesOrderLineItem(23));
    ODataEntry lineItemOdataEntry = new ODataEntryImpl(data1, mediaMetadata, entryMetadata, expandSelectTree);
    oDataEntryList.add(lineItemOdataEntry);
    FeedMetadataImpl feedMetadata = new FeedMetadataImpl();
    ODataFeedImpl odataFeedImpl = new ODataFeedImpl(oDataEntryList, feedMetadata);
    data.put("id", 1);
    data.put("SalesOrderLineItemDetails", odataFeedImpl);
    ODataEntryImpl odataEntry = new ODataEntryImpl(data, mediaMetadata, entryMetadata, expandSelectTree);
    odataEntry.setContainsInlineEntry(true);
    return odataEntry;
  }

  private Method getMethodForTesting(final String methodName, final Object object) {
    Method method = null;
    for (Method m : object.getClass().getDeclaredMethods()) {
      if (m.getName().equals(methodName)) {
        method = m;
        break;
      }
    }
    return method;
  }

}
