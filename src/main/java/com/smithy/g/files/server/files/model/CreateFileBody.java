package com.smithy.g.files.server.files.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.smithy.g.files.server.files.model.Identity;
import java.util.Arrays;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * CreateFileBody
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
public class CreateFileBody {

  private byte[] content;

  private String message;

  private String branch;

  private Identity author;

  private Identity committer;

  public CreateFileBody() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CreateFileBody(byte[] content, String message) {
    this.content = content;
    this.message = message;
  }

  public CreateFileBody content(byte[] content) {
    this.content = content;
    return this;
  }

  /**
   * Contenido binario del archivo (maximo 10MB)
   * @return content
  */
  @NotNull @Size(max = 10485760) 
  @Schema(name = "content", description = "Contenido binario del archivo (maximo 10MB)", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("content")
  public byte[] getContent() {
    return content;
  }

  public void setContent(byte[] content) {
    this.content = content;
  }

  public CreateFileBody message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Mensaje del commit
   * @return message
  */
  @NotNull @Size(min = 1, max = 500) 
  @Schema(name = "message", description = "Mensaje del commit", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("message")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public CreateFileBody branch(String branch) {
    this.branch = branch;
    return this;
  }

  /**
   * Branch destino (default: rama por defecto)
   * @return branch
  */
  
  @Schema(name = "branch", description = "Branch destino (default: rama por defecto)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("branch")
  public String getBranch() {
    return branch;
  }

  public void setBranch(String branch) {
    this.branch = branch;
  }

  public CreateFileBody author(Identity author) {
    this.author = author;
    return this;
  }

  /**
   * Get author
   * @return author
  */
  @Valid 
  @Schema(name = "author", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("author")
  public Identity getAuthor() {
    return author;
  }

  public void setAuthor(Identity author) {
    this.author = author;
  }

  public CreateFileBody committer(Identity committer) {
    this.committer = committer;
    return this;
  }

  /**
   * Get committer
   * @return committer
  */
  @Valid 
  @Schema(name = "committer", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("committer")
  public Identity getCommitter() {
    return committer;
  }

  public void setCommitter(Identity committer) {
    this.committer = committer;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateFileBody createFileBody = (CreateFileBody) o;
    return Arrays.equals(this.content, createFileBody.content) &&
        Objects.equals(this.message, createFileBody.message) &&
        Objects.equals(this.branch, createFileBody.branch) &&
        Objects.equals(this.author, createFileBody.author) &&
        Objects.equals(this.committer, createFileBody.committer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(Arrays.hashCode(content), message, branch, author, committer);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateFileBody {\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    branch: ").append(toIndentedString(branch)).append("\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    committer: ").append(toIndentedString(committer)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

