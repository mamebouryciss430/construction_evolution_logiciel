package fr.unantes.software.construction.attributes;

/** Created on 21/01/2018. */
public interface Event {

  Integer getId();

  String getName();

  void setName(String str);

  String getLocation();

  void setLocation(String str);

  /**
   * Adds a new alarm kind to this Event, until the limit of 5.
   *
   * @param kind the new alarm kind
   * @return true if the alarm is added, false if the limit is exceeded.
   */
  public boolean addAlarm(AlarmKind kind);

  /**
   * Gets the alarm kind stored at position {@code position}.
   *
   * @param position the alarm number position
   * @return an alarm kind
   */
  public AlarmKind getAlarm(int position);

  /**
   * Remove the alarm kind stored at positon {@code position}.
   *
   * @param position the alarm number position
   * @return the removed alarm kind
   */
  public AlarmKind removeAlarm(int position);
}
