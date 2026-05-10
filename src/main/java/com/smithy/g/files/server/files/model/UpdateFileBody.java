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
 * UpdateFileBody
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
public class UpdateFileBody {

  private String sha;

  private byte[] content;

  private String message;

  private String branch;

  private String fromPath;

  private Identity author;

  private Identity committer;

  public UpdateFileBody() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public UpdateFileBody(String sha, byte[] content, String message) {
    this.sha = sha;
    this.content = content;
    this.message = message;
  }

  public UpdateFileBody sha(String sha) {
    this.sha = sha;
    return this;
  }

  /**
   * SHA actual del archivo (para control de concurrencia)
   * @return sha
  */
  @NotNull 
  @Schema(name = "sha", description = "SHA actual del archivo (para control de concurrencia)", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("sha")
  public String getSha() {
    return sha;
  }

  public void setSha(String sha) {
    this.sha = sha;
  }

  public UpdateFileBody content(byte[] content) {
    this.content = content;
    return this;
  }

  /**
   * Nuevo contenido binario (maximo 10MB)
   * @return content
  */
  @NotNull @Size(max = 10485760) 
  @Schema(name = "content", description = "Nuevo contenido binario (maximo 10MB)", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("content")
  public byte[] getContent() {
    return content;
  }

  public void setContent(byte[] content) {
    this.content = content;
  }

  public UpdateFileBody message(String message) {
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

  public UpdateFileBody branch(String branch) {
    this.branch = branch;
    return this;
  }

  /**
   * Branch destino
   * @return branch
  */
  
  @Schema(name = "branch", description = "Branch destino", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("branch")
  public String getBranch() {
    return branch;
  }

  public void setBranch(String branch) {
    this.branch = branch;
  }

  public UpdateFileBody fromPath(String fromPath) {
    this.fromPath = fromPath;
    return this;
  }

  /**
   * Path original si es rename/move
   * @return fromPath
  */
  
  @Schema(name = "fromPath", description = "Path original si es rename/move", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("fromPath")
  public String getFromPath() {
    return fromPath;
  }

  public void setFromPath(String fromPath) {
    this.fromPath = fromPath;
  }

  public UpdateFileBody author(Identity author) {
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

  public UpdateFileBody committer(Identity committer) {
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
    UpdateFileBody updateFileBody = (UpdateFileBody) o;
    return Objects.equals(this.sha, updateFileBody.sha) &&
        Arrays.equals(this.content, updateFileBody.content) &&
        Objects.equals(this.message, updateFileBody.message) &&
        Objects.equals(this.branch, updateFileBody.branch) &&
        Objects.equals(this.fromPath, updateFileBody.fromPath) &&
        Objects.equals(this.author, updateFileBody.author) &&
        Objects.equals(this.committer, updateFileBody.committer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sha, Arrays.hashCode(content), message, branch, fromPath, author, committer);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateFileBody {\n");
    sb.append("    sha: ").append(toIndentedString(sha)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    branch: ").append(toIndentedString(branch)).append("\n");
    sb.append("    fromPath: ").append(toIndentedString(fromPath)).append("\n");
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

