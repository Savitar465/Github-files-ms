package com.smithy.g.files.server.files.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.smithy.g.files.server.files.model.DirectoryEntryDTO;
import com.smithy.g.files.server.files.model.FileContentDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Respuesta polimorfica: archivo o lista de entradas
 */

@Schema(name = "GetFileContentBody", description = "Respuesta polimorfica: archivo o lista de entradas")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
public class GetFileContentBody {

  private FileContentDTO file;

  @Valid
  private List<@Valid DirectoryEntryDTO> entries = new ArrayList<>();

  public GetFileContentBody file(FileContentDTO file) {
    this.file = file;
    return this;
  }

  /**
   * Get file
   * @return file
  */
  @Valid 
  @Schema(name = "file", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("file")
  public FileContentDTO getFile() {
    return file;
  }

  public void setFile(FileContentDTO file) {
    this.file = file;
  }

  public GetFileContentBody entries(List<@Valid DirectoryEntryDTO> entries) {
    this.entries = entries;
    return this;
  }

  public GetFileContentBody addEntriesItem(DirectoryEntryDTO entriesItem) {
    if (this.entries == null) {
      this.entries = new ArrayList<>();
    }
    this.entries.add(entriesItem);
    return this;
  }

  /**
   * Si es directorio, contiene la lista de entradas
   * @return entries
  */
  @Valid 
  @Schema(name = "entries", description = "Si es directorio, contiene la lista de entradas", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("entries")
  public List<@Valid DirectoryEntryDTO> getEntries() {
    return entries;
  }

  public void setEntries(List<@Valid DirectoryEntryDTO> entries) {
    this.entries = entries;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetFileContentBody getFileContentBody = (GetFileContentBody) o;
    return Objects.equals(this.file, getFileContentBody.file) &&
        Objects.equals(this.entries, getFileContentBody.entries);
  }

  @Override
  public int hashCode() {
    return Objects.hash(file, entries);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetFileContentBody {\n");
    sb.append("    file: ").append(toIndentedString(file)).append("\n");
    sb.append("    entries: ").append(toIndentedString(entries)).append("\n");
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

