package com.smithy.g.files.server.files.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * CreateFolderBody
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
public class CreateFolderBody {

  private String path;

  private String message;

  private String branch;

  public CreateFolderBody() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CreateFolderBody(String path, String message) {
    this.path = path;
    this.message = message;
  }

  public CreateFolderBody path(String path) {
    this.path = path;
    return this;
  }

  /**
   * Nombre/ruta de la carpeta
   * @return path
  */
  @NotNull @Pattern(regexp = "^[a-zA-Z0-9._/-]+$") @Size(min = 1, max = 255) 
  @Schema(name = "path", description = "Nombre/ruta de la carpeta", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("path")
  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public CreateFolderBody message(String message) {
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

  public CreateFolderBody branch(String branch) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateFolderBody createFolderBody = (CreateFolderBody) o;
    return Objects.equals(this.path, createFolderBody.path) &&
        Objects.equals(this.message, createFolderBody.message) &&
        Objects.equals(this.branch, createFolderBody.branch);
  }

  @Override
  public int hashCode() {
    return Objects.hash(path, message, branch);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateFolderBody {\n");
    sb.append("    path: ").append(toIndentedString(path)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    branch: ").append(toIndentedString(branch)).append("\n");
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

