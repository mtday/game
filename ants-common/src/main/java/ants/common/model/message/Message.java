package ants.common.model.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The base class for messages sent back and forth between the client and server.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Message {
}
