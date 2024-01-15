package com.github.brenoepics.at4j.util.rest;

/**
 * This is an enumeration representing the most commonly used HTTP methods. These methods are used
 * to perform actions on resources in a RESTFUL architecture.
 *
 * <ul>
 *   <li>GET: The GET method requests a representation of the specified resource. Requests using GET
 *       should only retrieve data.
 *   <li>POST: The POST method is used to submit an entity to the specified resource, often causing
 *       a change in state or side effects on the server.
 *   <li>PUT: The PUT method replaces all current representations of the target resource with the
 *       request payload.
 *   <li>DELETE: The DELETE method deletes the specified resource.
 *   <li>PATCH: The PATCH method is used to apply partial modifications to a resource.
 * </ul>
 */
public enum RestMethod {
  GET, // The GET method requests a representation of the specified resource.
  POST, // The POST method is used to submit an entity to the specified resource.
  PUT, // The PUT method replaces all current representations of the target resource with the request payload.
  DELETE, // The DELETE method deletes the specified resource.
  PATCH // The PATCH method is used to apply partial modifications to a resource.
}
