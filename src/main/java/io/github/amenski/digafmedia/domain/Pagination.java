package io.github.amenski.digafmedia.domain;

public record Pagination(int page, int size, long totalElements, int totalPages, boolean hasNext, boolean hasPrevious, boolean hasContent) {

  public static Pagination of(int page, int size, long totalElements) {
    int totalPages = (int) Math.ceil((double) totalElements / size);
    boolean hasNext = page < totalPages - 1;
    boolean hasPrevious = page > 0;
    boolean hasContent = totalElements > 0;
    return new Pagination(page, size, totalElements, totalPages, hasNext, hasPrevious, hasContent);
  }

  public int getOffset() {
    return page * size;
  }

  public int getFirstPage() {
    return 0;
  }

  public int getLastPage() {
    return Math.max(0, totalPages - 1);
  }

  public int getNextPage() {
    return hasNext ? page + 1 : page;
  }

  public int getPreviousPage() {
    return hasPrevious ? page - 1 : page;
  }

  public int getCurrentPageNumber() {
    return page + 1;
  }

  public long getStartElement() {
    return totalElements == 0 ? 0 : (page * size) + 1;
  }

  public long getEndElement() {
    if (totalElements == 0) return 0;
    long end = (page + 1) * size;
    return Math.min(end, totalElements);
  }
}
